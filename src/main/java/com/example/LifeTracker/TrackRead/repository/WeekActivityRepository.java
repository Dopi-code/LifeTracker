package com.example.LifeTracker.TrackRead.repository;

import com.example.LifeTracker.TrackRead.entity.WeekActivityTimes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekActivityRepository extends JpaRepository<WeekActivityTimes, Long> {
    WeekActivityTimes findByUserIdAndWeekNAndWeekAcName(Long userId, Long weekN, String weekAcName);
}