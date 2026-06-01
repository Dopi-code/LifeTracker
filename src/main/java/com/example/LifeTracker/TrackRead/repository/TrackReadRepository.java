package com.example.LifeTracker.TrackRead.repository;

import com.example.LifeTracker.TrackRead.entity.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrackReadRepository extends JpaRepository<Activities, Long> {
    List<Activities> findByUserIdAndTargetDate(Long userId, LocalDate targetDate);
    // MonthId가 Null인 데이터는 WeekId도 Null일 것이라고 상정.
    List<Activities> findByUserIdAndMonthIdIsNull(Long userId);
}
