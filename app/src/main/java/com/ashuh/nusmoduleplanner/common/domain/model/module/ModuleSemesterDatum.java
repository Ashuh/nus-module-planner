package com.ashuh.nusmoduleplanner.common.domain.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonMap;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ModuleSemesterDatum extends ModuleInfoSemesterDatum {
    @NonNull
    private final LessonMap lessons;

    public ModuleSemesterDatum(@NonNull Semester semester, @Nullable Exam exam,
                               @NonNull LessonMap lessons) {
        super(semester, exam);
        this.lessons = requireNonNull(lessons);
    }

    @NonNull
    public Set<LessonType> getLessonTypes() {
        return lessons.getLessonTypes();
    }

    @NonNull
    public List<Lesson> getAllLessons() {
        return lessons.getAllLessons();
    }

    @NonNull
    public List<Lesson> getLessons(LessonType lessonType) {
        return lessons.getLessons(lessonType);
    }

    @NonNull
    public Optional<Lesson> getLesson(LessonType lessonType, String classNo) {
        return lessons.get(lessonType, classNo);
    }

    @NonNull
    public Set<String> getLessonNos(LessonType lessonType) {
        return lessons.getLessonNos(lessonType);
    }
}
