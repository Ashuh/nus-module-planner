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
    public Optional<Integer> getWeekInterval() {
        return Optional.ofNullable(weekInterval);
    }

    public boolean isSingleDate() {
        return weeks.isEmpty() && hasDates() && start.isEqual(end);
    }

    private boolean hasDates() {
        return start != null && end != null;
    }

    public boolean isDateRange() {
        return weeks.isEmpty() && hasDates() && !start.isEqual(end);
    }

    public boolean isContinuous() {
        if (weeks.isEmpty() || isSingleWeek()) {
            return false;
        }
        Optional<Integer> lastWeek = lastWeek();
        Optional<Integer> firstWeek = firstWeek();
        assert lastWeek.isPresent();
        assert firstWeek.isPresent();
        return (lastWeek.get() - firstWeek.get()) == (weeks.size() - 1);
    }

    public boolean isSingleWeek() {
        return weeks.size() == 1;
    }

    @NonNull
    public Optional<Integer> lastWeek() {
        if (weeks.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(weeks.get(weeks.size() - 1));
    }

    @NonNull
    public Optional<Integer> firstWeek() {
        if (weeks.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(weeks.get(0));
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
    public List<Integer> getWeeks() {
        return weeks;
    }
}
