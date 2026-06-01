package com.example.LifeTracker.TrackRead.service;

import com.example.LifeTracker.TrackRead.dto.DailyTrackReadForm;
import com.example.LifeTracker.TrackRead.dto.MonthStatsDTO;
import com.example.LifeTracker.TrackRead.entity.Activities;
import com.example.LifeTracker.TrackRead.entity.MonthActivityTimes;
import com.example.LifeTracker.TrackRead.repository.MonthActivityRepository;
import com.example.LifeTracker.TrackRead.repository.TrackReadRepository;
import com.example.LifeTracker.TrackRead.repository.WeekActivityRepository;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

// 서비스: 정보를 처리하는 공간
@Service
@Slf4j
@ToString

public class TrackReadService {
    @Autowired
    private TrackReadRepository trackReadRepository;
    @Autowired
    WeekActivityRepository weekActivityRepository;
    @Autowired
    MonthActivityRepository monthActivityRepository;

    // 현재 날짜 AND 유저 ID - data요청
    public List<DailyTrackReadForm> nowIndex(Long userId) {
        LocalDate targetDate = LocalDate.now();
        List<Activities> activities = trackReadRepository.findByUserIdAndTargetDate(userId, targetDate);
        log.info("현재 날짜 data 요청: {}", activities.toString());
        return activities.stream()
                .map(DailyTrackReadForm::new) // 각 Activities 객체를 DTO로 변환
                .toList(); // 자바 16 이상 기준 (이하는 .collect(Collectors.toList()))
    }
    // 특정 날짜 AND 유저 ID - data요청
    public List<DailyTrackReadForm> targetDateIndex(LocalDate targetDate, Long userId) {
        List<Activities> activities = trackReadRepository.findByUserIdAndTargetDate(userId,targetDate);
        log.info("특정 날짜 data 요청: {}", activities.toString());
        return activities.stream()
                .map(DailyTrackReadForm::new)
                .toList();
    }

    // 활동 기록 중 '주/월'에 맞는 날짜가 매칭되지 않은 데이터들을 선별해 알맞는 날짜로 저장한다.
    // 날짜 주차 기준은 ISO. 4일 이상 포함되야 1주차.그 미만 0주차 월요일 ~> 일요일.
    // 예를 들어 5/4, 5/5, 5/6 날의 데이터는 "활동 이름" + [주간]0504 / [월간]0501로 묶인다.
    // 만약 [주간]0504데이터가 처음이라면 새로운 데이터로 저장된다.
    // 만약 [주간]0504데이터가 이미 있었다면, 주간 총 활동시간을 더한 값으로 수정한다.
    @Transactional  // save()없이 DB 값 수정하기 위함.
    public String calculateData(Long userId) {
        List<Activities> list = trackReadRepository.findByUserIdAndMonthIdIsNull(userId); // 통계 내기가 필요한 Activity 데이터
        log.info("[전체] {}개의 미정렬 월간 데이터 추출: {}", list.size(),list);

        // 개별 데이터에 맞는 주간/월간 값 찾기 (weekFields & monthFields)
        for (int i=0; i < list.size(); i++) {
            LocalDate targetDate = list.get(i).getTargetDate(); // 2026-05-06
            WeekFields weekFields = WeekFields.ISO; // 주간 데이터 기준
            Long calendarWeek = Long.valueOf(targetDate.get(weekFields.weekOfMonth())); // 20260506 => 1(주차)
            Long calendarMonth = Long.valueOf(targetDate.getMonthValue()); // 20260506 => 5(월)
            log.info("{}날짜의 \"{}\" 데이터가 선택되었습니다.", targetDate.toString(), list.get(i).getAcName());
            log.info("해당 데이터는 {}월 {}주차 데이터 입니다.", calendarMonth, calendarWeek);
            Long durationTime = duration(list.get(i).getAcStart(), list.get(i).getAcEnd());

            // 이미 월간 Table에 값이 있는가? 있으면 수정, 없으면 생성
            MonthActivityTimes findMonthAc = monthActivityRepository.findByUserIdAndMonthNAndMonthAcName(
                    list.get(i).getUserId(), calendarMonth, list.get(i).getAcName());

            if(findMonthAc!=null){  // 있을 때
                log.info("{}월의 \"{}\" 데이터가 이미 존재하므로, 기존 값에 합산합니다.", calendarMonth, list.get(i).getAcName());
                findMonthAc.setMonthAcTotal(findMonthAc.getMonthAcTotal()+durationTime);
                log.info("기존 시간: {} + 합산될 시간: {} => 합산 후 시간: {}", findMonthAc.getMonthAcTotal(), durationTime, findMonthAc.getMonthAcTotal()+durationTime);
            } else {    // 없을 때
                log.info("기존 데이터가 없으므로, 새로운 값으로 저장합니다");
                MonthActivityTimes newMonthAc = MonthActivityTimes.builder()
                        .userId(userId)
                        .monthAcName(list.get(i).getAcName())
                        .monthN(calendarMonth)
                        .monthAcTotal(durationTime)
                        .build();
                monthActivityRepository.save(newMonthAc);
                log.info("새로운 값으로 저장하였습니다: {}", newMonthAc.toString());
            }

//            // 해당 주의 값 DB에서 찾기 & 없으면 생성, 있으면 수정
//            WeekActivityTimes findWeekAc = weekActivityRepository.findByUserIdAndWeekNAndWeekAcName(
//                    list.get(i).getUserId(), calendarWeek, list.get(i).getAcName());
//            log.info("해당 주차의 DB값 찾기: {}", findWeekAc.toString());

//            if(findWeekAc != null) { // 이미 존재 -> 값 더하기
//                Long acTime = duration(list.get(i).getAcStart(), list.get(i).getAcEnd());
//                findWeekAc.setWeekAcTotal(findWeekAc.getWeekAcTotal() + acTime);
//            }
            // 해당 달의 값에 반영

            // 수정된 값 DB에 저장
        }
        log.info("★☆★☆★☆ 통계 내기가 완료되었습니다 :) ★☆★☆★☆");
        log.info("[전체] {}개의 미정렬 월간 데이터 통계냄", list.size());
        return null;
    }
    // acStart와 acEnd로 총 시간량 계산 => '분' 단위로 반환.
    public Long duration(Long acStart, Long acEnd) {
        // 시간 계산
        Long Min = 0l;
        if(acStart<acEnd) { // 1030 & 1210
            Long StMin = (acStart/100 * 60) + (acStart % 100);
            Long EndMin = (acEnd/100 * 60) + (acEnd % 100);

            Min = EndMin - StMin;
            log.info("[활동 시간 계산]시작 시간: {}, 끝 시간: {}, 계산된 시간(Min): {}", acStart, acEnd, Min);
        } else {
            log.info("시간 범위 설정 에러");
            return null;
        }
        return Min;
    }

    // 한 종류의 통계 정보 가져오기
    public List<MonthStatsDTO> getStats(Long userId, String acName) {

        return null;
    }
}
