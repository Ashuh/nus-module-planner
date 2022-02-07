package com.ashuh.nusmoduleplanner.data.module;

public enum SemesterType {
    SEMESTER_1(1, "Semester 1"),
    SEMESTER_2(2, "Semester 2"),
    SPECIAL_TERM_1(3, "Special Term I"),
    SPECIAL_TERM_2(4, "Special Term II");

    private final int id;
    private final String name;

    SemesterType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SemesterType fromId(int id) {
        switch (id) {
            case 1:
                return SEMESTER_1;
            case 2:
                return SEMESTER_2;
            case 3:
                return SPECIAL_TERM_1;
            case 4:
                return SPECIAL_TERM_2;
            default:
                throw new IllegalArgumentException("Invalid semester");
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

}
