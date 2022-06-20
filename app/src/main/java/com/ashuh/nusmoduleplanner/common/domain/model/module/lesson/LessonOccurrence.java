package com.ashuh.nusmoduleplanner.common.domain.model.module.lesson;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;


import com.ashuh.nusmoduleplanner.common.domain.model.module.Weeks;

import java.time.DayOfWeek;
import java.time.LocalTime;

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
        requireNonNull(day);
        requireNonNull(startTime);
        requireNonNull(endTime);
        requireNonNull(weeks);
        requireNonNull(venue);
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weeks = weeks;
        this.venue = venue;
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
}
