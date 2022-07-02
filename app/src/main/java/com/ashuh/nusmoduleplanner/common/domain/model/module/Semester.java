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
        this.value = value;
        this.name = requireNonNull(name);
    }

    public static Semester fromInt(int value) {
        for (Semester semester : values()) {
            if (semester.value == value) {
                return semester;
            }
        }
        throw new IllegalArgumentException("Invalid semester value: " + value);
    }

    public static Semester fromString(String name) {
        for (Semester semester : values()) {
            if (semester.name.equals(name)) {
                return semester;
            }
        }
        throw new IllegalArgumentException("Invalid semester name: " + name);
    }

    public int asInt() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

}
