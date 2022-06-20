package com.ashuh.nusmoduleplanner.common.domain.model.module.lesson;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

public enum LessonType {
    TUTORIAL("Tutorial", "TUT"),
    LECTURE("Lecture", "LEC"),
    PACKAGED_TUTORIAL("Packaged Tutorial", "PTUT"),
    PACKAGED_LECTURE("Packaged Lecture", "PLEC"),
    DESIGN_LECTURE("Design Lecture", "DLEC"),
    LAB("Laboratory", "LAB"),
    RECITATION("Recitation", "REC"),
    SECTIONAL("Sectional Teaching", "SEC");

    @NonNull
    private final String name;
    @NonNull
    private final String shortName;

    LessonType(@NonNull String name, @NonNull String shortName) {
        requireNonNull(name);
        requireNonNull(shortName);
        this.name = name;
        this.shortName = shortName;
    }

    @NonNull
    public String getShortName() {
        return shortName;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
