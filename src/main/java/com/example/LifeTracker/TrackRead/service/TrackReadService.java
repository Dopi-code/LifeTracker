package com.example.LifeTracker.TrackRead.service;

import com.example.LifeTracker.TrackRead.dto.DailyTrackReadForm;
import com.example.LifeTracker.TrackRead.entity.Activities;
import com.example.LifeTracker.TrackRead.entity.WeekActivityTimes;
import com.example.LifeTracker.TrackRead.repository.TrackReadRepository;
import com.example.LifeTracker.TrackRead.repository.WeekActivityRepository;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

// 서비스: 정보를 처리하는 공간
@Service
@Slf4j
@ToString
public class TrackReadService {
    @Autowired
    private TrackReadRepository trackReadRepository;
    @Autowired
    WeekActivityRepository weekActivityRepository;

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
    // 예를 들어 5/4, 5/5, 5/6 날의 데이터는 "활동 이름" + [주간]0504 / [월간]0501로 묶인다.
    // 만약 [주간]0504데이터가 처음이라면 새로운 데이터로 저장된다.
    // 만약 [주간]0504데이터가 이미 있었다면, 주간 총 활동시간을 더한 값으로 수정한다.
    public String calculateData(Long userId) {
        List<Activities> list = trackReadRepository.findByUserIdAndWeekIdIsNull(userId);
        log.info("[전체] 미정렬 주간 데이터 추출: {}", list);
        for (int i=0; i < list.size(); i++) {
            // 개별 데이터에 맞는 주간/월간 값 찾기 (weekFields & monthFields)
            LocalDate targetDate = list.get(i).getTargetDate(); // 2026-05-06
            log.info("날짜 데이터: {}", targetDate.toString());
            WeekFields weekFields = WeekFields.of(Locale.getDefault()); // 주간 데이터 기준
            Long calendarWeek = Long.valueOf(targetDate.get(weekFields.weekOfMonth())); // 20260506 => 1(주차)
            Long calendarMonth = Long.valueOf(targetDate.getMonthValue()); // 20260506 => 5(월)
            log.info("{}월 {}주차 데이터 입니다.", calendarMonth, calendarWeek);

            // 해당 월의 값 DB에서 찾기 & 없으면 생성, 있으면 수정


//            // 해당 주의 값 DB에서 찾기 & 없으면 생성, 있으면 수정
//            WeekActivityTimes findWeekAc = weekActivityRepository.findByUserIdAndWeekNAndWeekAcName(
//                    list.get(i).getUserId(), calendarWeek, list.get(i).getAcName());
//            log.info("해당 주차의 DB값 찾기: {}", findWeekAc.toString());

//            if(findWeekAc != null) { // 이미 존재 -> 값 더하기
//                Long acTime = timeCalculate(list.get(i).getAcStart(), list.get(i).getAcEnd());
//                findWeekAc.setWeekAcTotal(findWeekAc.getWeekAcTotal() + acTime);
//            }
            // 해당 달의 값에 반영

            // 수정된 값 DB에 저장
        }

        return null;
    }
    // acStart와 acEnd로 총 시간량 계산
    public Long timeCalculate(Long acStart, Long acEnd) {
        return null;
    }
}
