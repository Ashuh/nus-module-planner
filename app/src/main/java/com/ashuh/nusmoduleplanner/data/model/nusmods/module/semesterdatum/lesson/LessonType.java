package com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public enum LessonType {
    TUTORIAL("Tutorial", "TUT"),
    LECTURE("Lecture", "LEC"),
    PACKAGED_TUTORIAL("Packaged Tutorial", "PTUT"),
    PACKAGED_LECTURE("Packaged Lecture", "PLEC"),
    DESIGN_LECTURE("Design Lecture", "DLEC"),
    LAB("Laboratory", "LAB"),
    RECITATION("Recitation", "REC"),
    SECTIONAL("Sectional Teaching", "SEC");

    private static final Map<String, LessonType> STRING_LESSON_TYPE_MAP;

    static {
        STRING_LESSON_TYPE_MAP = new HashMap<>();

        for (LessonType type : LessonType.values()) {
            STRING_LESSON_TYPE_MAP.put(type.toString(), type);
        }
    }

    private final String name;
    private final String shortName;

    LessonType(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public static LessonType fromString(String s) {
        LessonType type = STRING_LESSON_TYPE_MAP.get(s);

        if (type == null) {
            throw new IllegalArgumentException("Invalid lesson type: " + s);
        }

        return type;
    }

    public String getShortName() {
        return shortName;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
