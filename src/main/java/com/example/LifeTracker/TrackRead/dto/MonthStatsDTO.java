package com.example.LifeTracker.TrackRead.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class MonthStatsDTO {
    private Long userId;
    private Long monthAcTotal;
}
