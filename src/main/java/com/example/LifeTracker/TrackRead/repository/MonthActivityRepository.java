package com.example.LifeTracker.TrackRead.repository;

import com.example.LifeTracker.TrackRead.entity.MonthActivityTimes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthActivityRepository extends JpaRepository<MonthActivityTimes, Long> {
    MonthActivityTimes findByUserIdAndMonthNAndMonthAcName(Long userId, Long monthN, String monthAcName);
}
