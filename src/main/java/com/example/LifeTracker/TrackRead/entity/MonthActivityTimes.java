package com.example.LifeTracker.TrackRead.entity;

import jakarta.persistence.*;
import lombok.*;

// 월간 활동 기록 합산
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonthActivityTimes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    // 소문자 뒤에 대문자가 딱 한 글자만 남는 경우 monthn으로 변환해버림(SpringBoot특징)
    @Column(name = "month_n")
    private Long monthN;

    @Column
    private String monthAcName;

    @Column
    private Long monthAcTotal;
}