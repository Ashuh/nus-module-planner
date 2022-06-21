package com.ashuh.nusmoduleplanner.modulelist.domain.usecase;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleInfo;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;

import java.util.List;

public class GetModuleInfoUseCase {
    @NonNull
    private final ModuleRepository moduleRepository;

    public GetModuleInfoUseCase(@NonNull ModuleRepository moduleRepository) {
        requireNonNull(moduleRepository);
        this.moduleRepository = moduleRepository;
    }

    public LiveData<List<ModuleInfo>> execute(AcademicYear academicYear) {
        return moduleRepository.getAllModuleInfo(academicYear.toString());
    }
}
