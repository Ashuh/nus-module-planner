package com.ashuh.nusmoduleplanner.data.model.nusmods;

import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SemesterDetail extends SemesterCondensed {
    @SerializedName("timetable")
    private final List<Lesson> timetable;
    @Ignore
    private transient final boolean isInitialized = false;
    @Ignore
    private transient Map<Lesson.Type, Map<String, List<Lesson>>> lessonCodeMap = null;

    public SemesterDetail(Semester semester, String examDate, int examDuration,
                          List<Lesson> timetable) {
        super(semester, examDate, examDuration);
        this.timetable = timetable;
    }

    private void init() {
        lessonCodeMap = new HashMap<>();

        for (Lesson lesson : timetable) {
            Lesson.Type type = lesson.getType();
            if (!lessonCodeMap.containsKey(type)) {
                lessonCodeMap.put(type, new HashMap<>());
            }

            if (!lessonCodeMap.get(type).containsKey(lesson.getClassNo())) {
                lessonCodeMap.get(type).put(lesson.getClassNo(), new ArrayList<>());
            }

            lessonCodeMap.get(type).get(lesson.getClassNo()).add(lesson);
        }
    }

    public List<Lesson> getTimetable() {
        return timetable;
    }

    public Set<Lesson.Type> getLessonTypes() {
        if (!isInitialized) {
            init();
        }

        return lessonCodeMap.keySet();
    }

    public List<Lesson> getLessons(Lesson.Type lessonType) {
        if (!isInitialized) {
            init();
        }

        List<Lesson> lessons = new ArrayList<>();
        for (List<Lesson> values : lessonCodeMap.get(lessonType).values()) {
            lessons.addAll(values);
        }

        return lessons;
    }

    public List<Lesson> getLessons(Lesson.Type lessonType, String lessonCode) {
        if (!isInitialized) {
            init();
        }

        return lessonCodeMap.get(lessonType).get(lessonCode);
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
