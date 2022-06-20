package com.ashuh.nusmoduleplanner.common.domain.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Weeks {
    @Nullable
    private final LocalDate start;
    @Nullable
    private final LocalDate end;
    @Nullable
    private final Integer weekInterval;
    @NonNull
    private final List<Integer> weeks;

    public Weeks(@NonNull LocalDate start, @NonNull LocalDate end, @Nullable Integer weekInterval,
                 @Nullable List<Integer> weeks) {
        requireNonNull(start);
        requireNonNull(end);
        this.start = start;
        this.end = end;
        this.weekInterval = weekInterval;
        this.weeks = weeks == null ? new ArrayList<>() : weeks;
    }

    public Weeks(@NonNull List<Integer> weeks) {
        requireNonNull(weeks);
        this.weeks = weeks;
        this.start = null;
        this.end = null;
        this.weekInterval = 0;
    }

    @NonNull
    public Optional<LocalDate> getStart() {
        return Optional.ofNullable(start);
    }

    @NonNull
    public Optional<LocalDate> getEnd() {
        return Optional.ofNullable(end);
    }

    @NonNull
    public Optional<Integer> getWeekInterval() {
        return Optional.ofNullable(weekInterval);
    }

    @NonNull
    public List<Integer> getWeeks() {
        return weeks;
    }
}
