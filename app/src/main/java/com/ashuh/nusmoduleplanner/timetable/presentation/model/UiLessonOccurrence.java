package com.ashuh.nusmoduleplanner.timetable.presentation.model;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonOccurrence;

import java.time.format.TextStyle;
import java.util.Locale;

public class UiLessonOccurrence {
    @NonNull
    private final String day;
    @NonNull
    private final String startTime;
    @NonNull
    private final String endTime;
    @NonNull
    private final String weeks;

    public UiLessonOccurrence(@NonNull String day, @NonNull String startTime,
                              @NonNull String endTime, @NonNull String weeks) {
        this.day = requireNonNull(day);
        this.startTime = requireNonNull(startTime);
        this.endTime = requireNonNull(endTime);
        this.weeks = requireNonNull(weeks);
    }

    @NonNull
    public String getDay() {
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
    public String getWeeks() {
        return weeks;
    }
}
