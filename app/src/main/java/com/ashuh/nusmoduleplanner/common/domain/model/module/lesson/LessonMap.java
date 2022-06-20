package com.ashuh.nusmoduleplanner.common.domain.model.module.lesson;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class LessonMap {
    @NonNull
    private final Map<LessonType, Map<String, Lesson>> map;

    public LessonMap() {
        this.map = new HashMap<>();
    }

    @NonNull
    public static LessonMap of(Collection<Lesson> lessons) {
        LessonMap lessonMap = new LessonMap();
        lessons.forEach(lessonMap::put);
        return lessonMap;
    }

    public void put(Lesson lesson) {
        if (lesson == null) {
            return;
        }

        LessonType lessonType = lesson.getLessonType();
        String classNo = lesson.getLessonNo();
        Optional<Lesson> existingLesson = get(lessonType, classNo);

        if (existingLesson.isPresent()) {
            existingLesson.get().addOccurrences(lesson.getOccurrences());
        } else {
            map.computeIfAbsent(lessonType, k -> new HashMap<>()).put(classNo, lesson);
        }
    }

    @NonNull
    public Optional<Lesson> get(LessonType lessonType, String lessonNo) {
        return Optional.ofNullable(map.get(lessonType))
                .map(lessonNoToLesson -> lessonNoToLesson.get(lessonNo));
    }

    @NonNull
    public Set<LessonType> getLessonTypes() {
        return map.keySet();
    }

    @NonNull
    public List<Lesson> getAllLessons() {
        List<Lesson> allLessons = new ArrayList<>();
        map.forEach((k, v) -> allLessons.addAll(v.values()));
        return allLessons;
    }

    @NonNull
    public List<Lesson> getLessons(LessonType lessonType) {
        if (!map.containsKey(lessonType)) {
            return Collections.emptyList();
        }
        Map<String, Lesson> lessonNoToLesson = map.get(lessonType);
        assert lessonNoToLesson != null;
        return new ArrayList<>(lessonNoToLesson.values());
    }

    @NonNull
    public Set<String> getLessonNos(LessonType lessonType) {
        if (!map.containsKey(lessonType)) {
            return Collections.emptySet();
        }
        Map<String, Lesson> lessonNoToLesson = map.get(lessonType);
        assert lessonNoToLesson != null;
        return lessonNoToLesson.keySet();
    }
}
