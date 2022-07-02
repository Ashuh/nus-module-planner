package com.ashuh.nusmoduleplanner.common.domain.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.time.Duration;
import java.time.ZonedDateTime;

public class Exam {
    @NonNull
    private final ZonedDateTime date;
    @NonNull
    private final Duration duration;

    public Exam(@NonNull ZonedDateTime date, @NonNull Duration duration) {
        this.date = requireNonNull(date);
        this.duration =  requireNonNull(duration);
    }

    @NonNull
    public ZonedDateTime getDate() {
        return date;
    }

    @NonNull
    public Duration getDuration() {
        return duration;
    }
}
