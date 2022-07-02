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
        this.moduleRepository = requireNonNull(moduleRepository);
    }

    public void execute(@NonNull String moduleCode, @NonNull Semester semester,
                        @NonNull LessonType lessonType, @NonNull String newLessonNo) {
        moduleRepository.updateLessonNoMapping(moduleCode, semester, lessonType, newLessonNo);
    }
}
