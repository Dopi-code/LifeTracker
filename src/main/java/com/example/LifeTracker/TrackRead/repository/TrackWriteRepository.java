package com.example.LifeTracker.TrackRead.repository;

import com.example.LifeTracker.TrackRead.entity.Activities;
import org.springframework.data.repository.CrudRepository;

public interface TrackWriteRepository extends CrudRepository<Activities, Long> {

}
