package com.ashuh.nusmoduleplanner.timetable.domain.usecase;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GetAlternateLessonsUseCase {
    @NonNull
    private final ModuleRepository moduleRepository;

    public GetAlternateLessonsUseCase(@NonNull ModuleRepository moduleRepository) {
        requireNonNull(moduleRepository);
        this.moduleRepository = moduleRepository;
    }

    @NonNull
    public LiveData<List<Lesson>> execute(AcademicYear academicYear, String moduleCode,
                                          Semester semester, LessonType lessonType,
                                          String currentLessonNo) {
        return Transformations.map(moduleRepository.getModule(academicYear.toString(), moduleCode),
                module -> module.getSemesterDatum(semester)
                        .map(datum -> datum.getLessons(lessonType))
                        .orElseThrow(() -> new IllegalStateException("No semester data"))
                        .stream()
                        .filter(lesson -> !lesson.getLessonNo().equals(currentLessonNo))
                        .collect(Collectors.toList()));
    }
}
