package com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public enum SemesterType {
    SEMESTER_1(1, "Semester 1"),
    SEMESTER_2(2, "Semester 2"),
    SPECIAL_TERM_1(3, "Special Term I"),
    SPECIAL_TERM_2(4, "Special Term II");

    private static final Map<Integer, SemesterType> INTEGER_SEMESTER_MAP;

    static {
        INTEGER_SEMESTER_MAP = new HashMap<>();
        for (SemesterType semesterType : values()) {
            INTEGER_SEMESTER_MAP.put(semesterType.getId(), semesterType);
        }
    }

    private final int id;
    private final String name;

    SemesterType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SemesterType fromId(int id) {
        if (!INTEGER_SEMESTER_MAP.containsKey(id)) {
            throw new IllegalArgumentException("No semester type with id " + id);
        }
        return INTEGER_SEMESTER_MAP.get(id);
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

}
