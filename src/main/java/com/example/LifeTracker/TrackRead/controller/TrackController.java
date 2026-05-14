package com.example.LifeTracker.TrackRead.controller;

import com.example.LifeTracker.TrackRead.dto.DailyTrackReadForm;
import com.example.LifeTracker.TrackRead.dto.DailyTrackWriteForm;
import com.example.LifeTracker.TrackRead.entity.Activities;
import com.example.LifeTracker.TrackRead.repository.TrackWriteRepository;
import com.example.LifeTracker.TrackRead.service.TrackReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.web.ProjectedPayload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

// View Controller
// 기본 구조 = Service 계층에서 데이터를 정제해오고, 그 데이터를 model에 담아 출력한다.
// 중요! header.mustache에서 model값으로 userId를 쓴다. 필수!
@Controller
@Slf4j
public class TrackController {
    @Autowired
    TrackReadService trackReadService;
    @Autowired
    TrackWriteRepository trackWriteRepository;

    //- 메인으로 날짜 정보와 함께 redirect 선택 화면
    // Redirect 시 날짜 형식을 통일하기 위해 String 타입으로 특정함(예외적).
    @GetMapping("/lifeTracker/main/{userId}")
    public String mainPageRedirect(@PathVariable Long userId,
                                   RedirectAttributes rttr) {
        String targetDate = LocalDate.now().toString();
        log.info("main페이지로 리다이렉트 시도. 날짜: {}, 유저ID: {}", targetDate, userId);
        rttr.addAttribute("targetDate", targetDate);
        rttr.addAttribute("userId", userId);
        return "redirect:/lifeTracker/main/{targetDate}/{userId}";
    }

    //- 메인 선택 화면
    @GetMapping("/lifeTracker/main/{targetDate}/{userId}")
    public String mainPage(@PathVariable Long userId,
                           @PathVariable LocalDate targetDate,
                           Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("targetDate", targetDate);
        return "/mainPage";
    }

    //- 특정 날짜의 활동 기록 페이지
    @GetMapping("/lifeTracker/new/{targetDate}/{userId}")
    public String newActivities(@PathVariable Long userId,
                                @PathVariable LocalDate targetDate,
                                Model model,
                                RedirectAttributes rttr) {
        List<DailyTrackReadForm> dailyTrackReadFormList = trackReadService.targetDateIndex(targetDate, userId);
        model.addAttribute("userId", userId);
        model.addAttribute("targetDate", targetDate);
        model.addAttribute("DailyTrackReadFormList", dailyTrackReadFormList);
        log.info("new 뷰 페이지 호출");
        rttr.addAttribute("targetDate", targetDate);
        return "/new";
    }

    //- 활동 기록 페이지: 데이터 추가
    @PostMapping("/lifeTracker/new/{targetDate}/{userId}")
    public String create(DailyTrackWriteForm form,
                         @PathVariable LocalDate targetDate,
                         @PathVariable Long userId) {
        log.info("form데이터 추가 요청: {}", form.toString());
        Activities activities = form.toEntity();
        log.info("시간 데이터의 타입(Long이어야함): {}", activities.getAcStart().getClass());
        return "";
    }

    //- 활동 기록 페이지: 데이터 수정/삭제(수정 시 전체 덮어씌우기 예정)
    // 통계 페이지
    @GetMapping("/lifeTracker/stats/{targetDate}/{userId}")
    public String stats(@PathVariable Long userId,
                        @PathVariable LocalDate targetDate,
                        Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("targetDate", targetDate);
        return "/stats";
    }
}
