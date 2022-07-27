package com.ashuh.nusmoduleplanner.common.domain.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModuleReading {
    @NonNull
    private final Module module;
    @NonNull
    private final Semester semester;
    @NonNull
    private final Map<LessonType, String> lessonTypeToLessonNo;
    private final int colorId;

    public ModuleReading(@NonNull Module module, @NonNull Semester semester,
                         @NonNull Map<LessonType, String> lessonTypeToLessonNo, int colorId) {
        this.module = requireNonNull(module);
        this.semester = requireNonNull(semester);
        this.lessonTypeToLessonNo = requireNonNull(lessonTypeToLessonNo);
        this.colorId = colorId;
        assert colorId >= 0 && colorId < 8;

        if (!isValidReading(module, semester, lessonTypeToLessonNo)) {
            throw new IllegalArgumentException("Invalid ModuleReading");
        }
    }

    private static boolean isValidReading(Module module, Semester semester,
                                          Map<LessonType, String> lessonTypeToLessonNo) {
        Optional<ModuleSemesterDatum> datum = module.getSemesterDatum(semester);
        if (!datum.isPresent()) {
            return false;
        }

        for (Map.Entry<LessonType, String> entry : lessonTypeToLessonNo.entrySet()) {
            LessonType lessonType = entry.getKey();
            String lessonNo = entry.getValue();
            Optional<Lesson> lesson = datum.get().getLesson(lessonType, lessonNo);
            if (!lesson.isPresent()) {
                return false;
            }
        }
        return true;
    }

    @NonNull
    public static ModuleReading withDefaultLessonMapping(@NonNull Module module,
                                                         @NonNull Semester semester,
                                                         int color) {
        requireNonNull(module);
        requireNonNull(semester);

        ModuleSemesterDatum datum = module.getSemesterDatum(semester)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No semester data found for semester " + semester));

        Map<LessonType, String> lessonTypeToLessonNo = new HashMap<>();
        datum.getLessonTypes().forEach(lessonType -> {
            List<Lesson> lessons = datum.getLessons(lessonType);
            String defaultLessonNo = lessons.stream()
                    .findAny()
                    .map(Lesson::getLessonNo)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No lessons found for lesson type " + lessonType));

            lessonTypeToLessonNo.put(lessonType, defaultLessonNo);
        });
        return new ModuleReading(module, semester, lessonTypeToLessonNo, color);
    }

    @NonNull
    public Module getModule() {
        return module;
    }

    @NonNull
    public Semester getSemester() {
        return semester;
    }

    public int getColorId() {
        return colorId;
    }

    @NonNull
    public List<Lesson> getAssignedLessons() {
        List<Lesson> lessons = new ArrayList<>();
        lessonTypeToLessonNo.forEach((lessonType, lessonNo) -> {
            Optional<Lesson> lesson = getLesson(lessonType, lessonNo);
            assert lesson.isPresent();
            lessons.add(lesson.get());
        });
        return lessons;
    }

    @NonNull
    public Optional<Lesson> getLesson(LessonType lessonType, String classNo) {
        return getSemesterDatum().getLesson(lessonType, classNo);
    }

    @NonNull
    public ModuleSemesterDatum getSemesterDatum() {
        Optional<ModuleSemesterDatum> datum = module.getSemesterDatum(semester);
        assert datum.isPresent();
        return datum.get();
    }

    @NonNull
    public Map<LessonType, String> getLessonNoMapping() {
        return lessonTypeToLessonNo;
    }

    @NonNull
    public Optional<Exam> getExam() {
        return getSemesterDatum().getExam();
    }
}
