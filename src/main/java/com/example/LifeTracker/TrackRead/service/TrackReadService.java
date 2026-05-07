package com.example.LifeTracker.TrackRead.service;

import com.example.LifeTracker.TrackRead.dto.DailyTrackReadForm;
import com.example.LifeTracker.TrackRead.entity.Activities;
import com.example.LifeTracker.TrackRead.repository.TrackReadRepository;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

// 서비스: 정보를 처리하는 공간
@Service
@Slf4j
@ToString
public class TrackReadService {
    @Autowired
    private TrackReadRepository trackReadRepository;

    // 현재 날짜 data요청
    public List<DailyTrackReadForm> nowIndex(Long userId) {
        LocalDate nowDate = LocalDate.now();
        List<Activities> activities = trackReadRepository.findByUserIdAndNowDate(userId, nowDate);
        return activities.stream()
                .map(DailyTrackReadForm::new) // 각 Activities 객체를 DTO로 변환
                .toList(); // 자바 16 이상 기준 (이하는 .collect(Collectors.toList()))
    }
    // 특정 날짜 data 요청

}
