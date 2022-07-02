package com.ashuh.nusmoduleplanner.timetable.domain.usecase;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;

import java.util.List;

public class GetModuleReadingsUseCase {
    @NonNull
    private final ModuleRepository moduleRepository;

    public GetModuleReadingsUseCase(@NonNull ModuleRepository moduleRepository) {
        this.moduleRepository = requireNonNull(moduleRepository);
    }

    @NonNull
    public LiveData<List<ModuleReading>> execute(@NonNull Semester semester) {
        return moduleRepository.getModuleReadings(semester);
    }
}
