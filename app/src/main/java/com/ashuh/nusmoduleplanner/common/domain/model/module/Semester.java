package com.ashuh.nusmoduleplanner.common.domain.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

public enum Semester {
    SEMESTER_1(1, "Semester 1"),
    SEMESTER_2(2, "Semester 2"),
    SPECIAL_TERM_1(3, "Special Term I"),
    SPECIAL_TERM_2(4, "Special Term II");

    private final int value;
    @NonNull
    private final String name;

    Semester(int value, @NonNull String name) {
        requireNonNull(name);
        this.value = value;
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public int asInt() {
        return value;
    }
}
