package com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.lessonoccurrence.LessonOccurrenceEntity;

@Entity(
        foreignKeys = @ForeignKey(
                entity = LessonOccurrenceEntity.class,
                parentColumns = "id",
                childColumns = "ownerId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(
                value = {"ownerId"},
                unique = true
        )
)
public class WeeksEntity {
    @Nullable
    private final String start;
    @Nullable
    private final String end;
    @Nullable
    private final Integer weekInterval;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long ownerId;

    public WeeksEntity(@Nullable String start, @Nullable String end,
                       @Nullable Integer weekInterval) {
        this.start = start;
        this.end = end;
        this.weekInterval = weekInterval;
    }

    @Nullable
    public String getStart() {
        return start;
    }

    @Nullable
    public String getEnd() {
        return end;
    }

    @Nullable
    public Integer getWeekInterval() {
        return weekInterval;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}


