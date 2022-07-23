package com.ashuh.nusmoduleplanner.common.domain.model.module.lesson;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Weeks;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public class LessonOccurrence {
    @NonNull
    private final DayOfWeek day;
    @NonNull
    private final LocalTime startTime;
    @NonNull
    private final LocalTime endTime;
    @NonNull
    private final Weeks weeks;
    @NonNull
    private final String venue;

    public LessonOccurrence(@NonNull DayOfWeek day, @NonNull LocalTime startTime,
                            @NonNull LocalTime endTime, @NonNull Weeks weeks,
                            @NonNull String venue) {
        this.day = requireNonNull(day);
        this.startTime = requireNonNull(startTime);
        this.endTime = requireNonNull(endTime);
        this.weeks = requireNonNull(weeks);
        this.venue = requireNonNull(venue);
    }

    @NonNull
    public DayOfWeek getDay() {
        return day;
    }

    @NonNull
    public LocalTime getStartTime() {
        return startTime;
    }

    @NonNull
    public LocalTime getEndTime() {
        return endTime;
    }

    @NonNull
    public Weeks getWeeks() {
        return weeks;
    }

    @NonNull
    public String getVenue() {
        return venue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, startTime, endTime, weeks, venue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LessonOccurrence that = (LessonOccurrence) o;
        return day == that.day
                && startTime.equals(that.startTime)
                && endTime.equals(that.endTime)
                && weeks.equals(that.weeks)
                && venue.equals(that.venue);
    }
}
