package com.example.LifeTracker.TrackRead.controller;

import com.example.LifeTracker.TrackRead.dto.DailyTrackReadForm;
import com.example.LifeTracker.TrackRead.entity.Activities;
import com.example.LifeTracker.TrackRead.service.TrackReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.ProjectedPayload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

// View Controller
// 기본 구조 = Service 계층에서 데이터를 정제해오고, 그 데이터를 model에 담아 출력한다.
// 중요! header.mustache에서 model값으로 userId를 쓴다. 필수!
@Controller
@Slf4j
public class TrackController {
    @Autowired
    TrackReadService trackReadService;
    //- 메인 선택 화면
    @GetMapping("/lifeTracker/main/{userId}")
    public String mainPage(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "/mainPage";
    }
//    //- 활동 기록 페이지: 조회시 현재 날짜로 조회
    // 문제가 있어 일단 대기
//    @GetMapping("/lifeTracker/new/{userId}")
//    public String newActivities(@PathVariable Long userId, Model model) {
//        List<DailyTrackReadForm> dailyTrackReadFormList = trackReadService.nowIndex(userId);
//        model.addAttribute("userId", userId);
//        model.addAttribute("DailyTrackReadFormList", dailyTrackReadFormList);
//        log.info("뭔가가 되고있다... {}", dailyTrackReadFormList.toString());
//        return "/new";
//    }

    //- 특정 날짜 활동 기록 페이지
    @GetMapping("/lifeTracker/new/{targetDate}/{userId}")
    public String newActivities(@PathVariable Long userId,
                                @PathVariable LocalDate targetDate,
                                Model model) {
        List<DailyTrackReadForm> dailyTrackReadFormList = trackReadService.targetDateIndex(targetDate, userId);
        model.addAttribute("userId", userId);
        model.addAttribute("DailyTrackReadFormList", dailyTrackReadFormList);
        log.info("뭔가가 되고있다... {}", dailyTrackReadFormList.toString());
        return "/new";
    }
    //- 활동 기록 페이지: 데이터 추가
    //- 활동 기록 페이지: 데이터 수정/삭제(수정 시 전체 덮어씌우기 예정)
    // 통계 페이지
    @GetMapping("/lifeTracker/stats/{userId}")
    public String stats(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "/stats";
    }
}
