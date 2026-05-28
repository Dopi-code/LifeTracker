package com.example.LifeTracker.TrackRead.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 주간 활동 기록 합산
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WeekActivityTimes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    // 소문자 뒤에 대문자가 딱 한 글자만 남는 경우 weekn으로 변환해버림(SpringBoot특징)
    @Column(name = "week_n")
    private Long weekN;

    @Column
    private String wAcName;

    @Column
    private Long wAcTotal;
}
