package com.example.LifeTracker.TrackRead.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

// 일일 활동 기록
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Activities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long ctgId;

    @Column(nullable = true)
    private String acName;

    @Column(nullable = false)
    private Long acStart;

    @Column(nullable = false)
    private Long acEnd;

    @Column(nullable = false)
    private LocalDate targetDate;

    @Column(nullable = true)
    private Integer weekId;

    @Column(nullable = true)
    private Integer monthId;

    @Column(nullable = false)
    private String acColor;
}
