package com.ashuh.nusmoduleplanner.moduledetail.presentation.model;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

public class UiExam {
    @NonNull
    private final String date;
    @NonNull
    private final String duration;

    public UiExam(@NonNull String date, @NonNull String duration) {
        requireNonNull(date);
        requireNonNull(duration);
        this.date = date;
        this.duration = duration;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public String getDuration() {
        return duration;
    }
}
