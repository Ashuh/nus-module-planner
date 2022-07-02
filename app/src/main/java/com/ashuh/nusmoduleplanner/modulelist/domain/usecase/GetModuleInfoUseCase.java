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
        this.moduleRepository = requireNonNull(moduleRepository);
    }

    @NonNull
    public LiveData<List<ModuleInfo>> execute(@NonNull AcademicYear academicYear) {
        return moduleRepository.getAllModuleInfo(academicYear.toString());
    }
}
