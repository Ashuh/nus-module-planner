package com.ashuh.nusmoduleplanner.timetable.domain.usecase;

import static java.util.Objects.requireNonNull;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;

public class UpdateColorUseCase {
    @NonNull
    private final ModuleRepository moduleRepository;

    public UpdateColorUseCase(@NonNull ModuleRepository moduleRepository) {
        this.moduleRepository = requireNonNull(moduleRepository);
    }

    public void execute(@NonNull String moduleCode, @NonNull Semester semester,
                        @NonNull Color newColor) {
        moduleRepository.updateColor(moduleCode, semester, newColor);
    }
}
