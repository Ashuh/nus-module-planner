package com.ashuh.nusmoduleplanner.timetable.domain.usecase;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;

public class UpdateLessonNoUseCase {
    @NonNull
    private final ModuleRepository moduleRepository;

    public UpdateLessonNoUseCase(@NonNull ModuleRepository moduleRepository) {
        requireNonNull(moduleRepository);
        this.moduleRepository = moduleRepository;
    }

    public void execute(String moduleCode, Semester semester, LessonType lessonType,
                        String newLessonNo) {
        moduleRepository.updateLessonNoMapping(moduleCode, semester, lessonType, newLessonNo);
    }
}
