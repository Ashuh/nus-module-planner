package com.ashuh.nusmoduleplanner.data.model.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SemesterDetail extends SemesterCondensed {
    @SerializedName("timetable")
    private final List<Lesson> timetable;

    public SemesterDetail(Semester semester, String examDate, int examDuration,
                          List<Lesson> timetable) {
        super(semester, examDate, examDuration);
        this.timetable = timetable;
    }

    public List<Lesson> getLessons() {
        return timetable;
    }

    @Override
    public String toString() {
        return "SemesterData{" +
                "semester=" + semester +
                ", examDate='" + examDate + '\'' +
                ", examDuration=" + examDuration +
                ", timetable=" + timetable +
                '}';
    }
}
