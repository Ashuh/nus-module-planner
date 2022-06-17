package com.ashuh.nusmoduleplanner.common.data.api.model.module.weeks;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ApiWeekRange {
    @NonNull
    @SerializedName("start")
    private final String start;
    @NonNull
    @SerializedName("end")
    private final String end;
    @Nullable
    @SerializedName("weekInterval")
    private final Integer weekInterval;
    @Nullable
    @SerializedName("weeks")
    private final List<Integer> weeks;

    public ApiWeekRange(@NonNull String start, @NonNull String end, @Nullable Integer weekInterval,
                        @Nullable List<Integer> weeks) {
        requireNonNull(start);
        requireNonNull(end);
        this.start = start;
        this.end = end;
        this.weekInterval = weekInterval;
        this.weeks = weeks;
    }

    @NonNull
    public String getStart() {
        return start;
    }

    @NonNull
    public String getEnd() {
        return end;
    }

    @NonNull
    public Optional<Integer> getWeekInterval() {
        return Optional.ofNullable(weekInterval);
    }

    @NonNull
    public List<Integer> getWeeks() {
        return requireNonNullElse(weeks, Collections.emptyList());
    }
}
