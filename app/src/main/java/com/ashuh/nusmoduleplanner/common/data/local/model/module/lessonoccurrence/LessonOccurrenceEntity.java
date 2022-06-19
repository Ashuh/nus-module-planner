package com.ashuh.nusmoduleplanner.common.data.local.model.module.lessonoccurrence;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.lesson.LessonEntity;

import java.time.DayOfWeek;
import java.util.Objects;

@Entity(
        foreignKeys = @ForeignKey(
                entity = LessonEntity.class,
                parentColumns = "id",
                childColumns = "ownerId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(
                value = {"ownerId", "day", "startTime", "endTime", "venue"},
                unique = true
        )
)
public class LessonOccurrenceEntity {
    @NonNull
    private final DayOfWeek day;
    @NonNull
    private final String startTime;
    @NonNull
    private final String endTime;
    @NonNull
    private final String venue;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long ownerId;

    public LessonOccurrenceEntity(@NonNull DayOfWeek day, @NonNull String startTime,
                                  @NonNull String endTime, @NonNull String venue) {
        requireNonNull(day);
        requireNonNull(startTime);
        requireNonNull(endTime);
        requireNonNull(venue);
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
    }

    @NonNull
    public DayOfWeek getDay() {
        return day;
    }

    @NonNull
    public String getStartTime() {
        return startTime;
    }

    @NonNull
    public String getEndTime() {
        return endTime;
    }

    @NonNull
    public String getVenue() {
        return venue;
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

    public void setOwnerId(long id) {
        this.ownerId = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, startTime, endTime, venue, id, ownerId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LessonOccurrenceEntity that = (LessonOccurrenceEntity) o;
        return id == that.id
                && ownerId == that.ownerId
                && day == that.day
                && startTime.equals(that.startTime)
                && endTime.equals(that.endTime)
                && venue.equals(that.venue);
    }

    @NonNull
    @Override
    public String toString() {
        return "LessonOccurrenceEntity{"
                + "day='" + day + '\''
                + ", startTime='" + startTime + '\''
                + ", endTime='" + endTime + '\''
                + ", id=" + id
                + ", ownerId=" + ownerId
                + '}';
    }
}
