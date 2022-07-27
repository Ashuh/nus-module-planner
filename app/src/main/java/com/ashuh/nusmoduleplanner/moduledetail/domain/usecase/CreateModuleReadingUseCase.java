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
        this.moduleRepository = requireNonNull(moduleRepository);
    }

    public void execute(@NonNull Module module, @NonNull Semester semester) {
        Color color = ColorScheme.GOOGLE.getRandomColor();
        int colorId =  0;
        ModuleReading moduleReading =
                ModuleReading.withDefaultLessonMapping(module, semester, colorId);
        moduleRepository.storeModuleReading(moduleReading);
    }
}
