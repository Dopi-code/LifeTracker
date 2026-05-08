package com.example.LifeTracker.TrackRead.dto;
// Server가 User에게 주는 데이터의 단위기준 - 일일 활동 기록
// 데이터 타입 후에 수정 예정

import com.example.LifeTracker.TrackRead.entity.Activities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class DailyTrackReadForm {
    private Long id;
    private Long userId;
    private Long ctgId;
    private String acName;
    private Long acStart;
    private Long acEnd;
    private LocalDate targetDate;

    // Activities 엔티티를 직접 받는 생성자
    public DailyTrackReadForm(Activities entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.ctgId = entity.getCtgId();
        this.acName = entity.getAcName();
        this.acStart = entity.getAcStart();
        this.acEnd = entity.getAcEnd();
        this.targetDate = entity.getTargetDate();
    }
}
