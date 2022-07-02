package com.ashuh.nusmoduleplanner.moduledetail.presentation.model;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

public class UiSemester {
    @NonNull
    private final String semester;
    @NonNull
    private final Runnable onClick;

    public UiSemester(@NonNull String semester, @NonNull Runnable onClick) {
        this.semester =  requireNonNull(semester);
        this.onClick = requireNonNull(onClick);
    }

    @NonNull
    public String getSemester() {
        return semester;
    }

    public void onClick() {
        onClick.run();
    }
}
