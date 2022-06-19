package com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        primaryKeys = {"weekId", "weekNumber"},
        foreignKeys = @ForeignKey(
                entity = WeeksEntity.class,
                parentColumns = "id",
                childColumns = "weekId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(
                value = "weekNumber"
        )
)
public class WeeksWeekNumberCrossRefEntity {
    private final long weekId;
    private final int weekNumber;

    public WeeksWeekNumberCrossRefEntity(long weekId, int weekNumber) {
        this.weekId = weekId;
        this.weekNumber = weekNumber;
    }


    public long getWeekId() {
        return weekId;
    }

    public int getWeekNumber() {
        return weekNumber;
    }
}
