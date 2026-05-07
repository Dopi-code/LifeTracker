package com.example.LifeTracker.TrackRead.api;

import com.example.LifeTracker.TrackRead.dto.DailyTrackReadForm;
import com.example.LifeTracker.TrackRead.service.TrackReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
@RestController
public class TrackReadApiController {
    @Autowired
    private TrackReadService trackReadService;

    // 현재 날짜 활동 기록 조회
    @GetMapping("/activities/now/{userId}")
    public List<DailyTrackReadForm> nowIndex(@PathVariable Long userId) {
        return trackReadService.nowIndex(userId);
    }

    // 특정 날짜 활동 기록 조회
//    @GetMapping("/activities/{targetDate}/{userId}")
//    public List<DailyTrackReadForm> targetDateIndex(
//            @PathVariable LocalDate targetDate,
//            @PathVariable Long userId) {
//        trackReadService.targetDateIndex(targetDate, userId);
//        return null;
//    }
}
