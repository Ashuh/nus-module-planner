package com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = @Index(
                value = "weekNumber",
                unique = true
        )
)
public class WeekNumberEntity {
    @PrimaryKey
    private final int weekNumber;

    public WeekNumberEntity(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getWeekNumber() {
        return weekNumber;
    }
}
