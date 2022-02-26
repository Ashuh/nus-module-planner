package com.ashuh.nusmoduleplanner.data.model.nusmods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Timetable {

    private final Map<Lesson.Type, Map<String, List<Lesson>>> lessonTypeMap = new HashMap<>();

    public Timetable(List<Lesson> lessons) {

        for (Lesson lesson : lessons) {
            Lesson.Type type = lesson.getType();
            if (!lessonTypeMap.containsKey(type)) {
                lessonTypeMap.put(type, new HashMap<>());
            }

            if (!lessonTypeMap.get(type).containsKey(lesson.getClassNo())) {
                lessonTypeMap.get(type).put(lesson.getClassNo(), new ArrayList<>());
            }

            lessonTypeMap.get(type).get(lesson.getClassNo()).add(lesson);
        }
    }

    public Set<Lesson.Type> getLessonTypes() {
        return lessonTypeMap.keySet();
    }

    public List<Lesson> getLessons(Lesson.Type lessonType) {
        List<Lesson> lessons = new ArrayList<>();
        for (List<Lesson> values : lessonTypeMap.getOrDefault(lessonType, new HashMap<>())
                .values()) {
            lessons.addAll(values);
        }

        return lessons;
    }

    public List<Lesson> getLessons(Lesson.Type lessonType, String lessonCode) {
        return lessonTypeMap.getOrDefault(lessonType, new HashMap<>())
                .getOrDefault(lessonCode, new ArrayList<>());
    }
}
