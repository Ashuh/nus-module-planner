package com.ashuh.nusmoduleplanner.timetable.domain.usecase;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme.Index;

public class UpdateColorUseCase {
    @NonNull
    private final ModuleRepository moduleRepository;

    public UpdateColorUseCase(@NonNull ModuleRepository moduleRepository) {
        this.moduleRepository = requireNonNull(moduleRepository);
    }

    public void execute(@NonNull String moduleCode, @NonNull Semester semester, int newColorId) {
        moduleRepository.updateColor(moduleCode, semester, new Index(newColorId));
    }
}
