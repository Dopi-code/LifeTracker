package com.example.LifeTracker.TrackRead.dto;

import com.example.LifeTracker.TrackRead.entity.Activities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
@AllArgsConstructor
@ToString
@Getter
public class DailyTrackWriteForm {
    private Long id;
    private Long userId;
    private Long ctgId;
    private String acName;
    private String acStart;
    private String acEnd;
    private LocalDate targetDate;
    private String acColor;

    public Activities toEntity() {
        Long acStartLong = convertTimeToLong(acStart);
        Long acEndLong = convertTimeToLong(acEnd);
        Integer weekId = null;
        Integer monthId = null;
        return new Activities(id, userId, ctgId, acName, acStartLong, acEndLong,targetDate, weekId, monthId, acColor);
    }

    private Long convertTimeToLong(String timeStr) {
        Integer len = timeStr.length();
        String hour = timeStr.substring(0,2);
        String minute = timeStr.substring(3,5);
        Long targetTime = Long.parseLong(hour+minute);
        return targetTime;
    }
}
