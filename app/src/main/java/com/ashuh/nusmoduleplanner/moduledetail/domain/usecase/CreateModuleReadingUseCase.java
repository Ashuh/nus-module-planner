package com.ashuh.nusmoduleplanner.moduledetail.domain.usecase;

import static java.util.Objects.requireNonNull;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

public class CreateModuleReadingUseCase {
    @NonNull
    private final ModuleRepository moduleRepository;

    public CreateModuleReadingUseCase(@NonNull ModuleRepository moduleRepository) {
        requireNonNull(moduleRepository);
        this.moduleRepository = moduleRepository;
    }

    public void execute(Module module, Semester semester) {
        Color color = ColorScheme.GOOGLE.getRandomColor();
        ModuleReading moduleReading =
                ModuleReading.withDefaultLessonMapping(module, semester, color);
        moduleRepository.storeModuleReading(moduleReading);
    }
}
