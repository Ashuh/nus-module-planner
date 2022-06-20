package com.ashuh.nusmoduleplanner.moduledetail;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;

import org.jetbrains.annotations.NotNull;

public class ModuleDetailViewModelFactory implements ViewModelProvider.Factory {
    private final ModuleRepository moduleRepository;
    private final AcademicYear acadYear;
    private final String moduleCode;

    public ModuleDetailViewModelFactory(ModuleRepository moduleRepository, AcademicYear acadYear,
                                        String moduleCode) {
        this.moduleRepository = moduleRepository;
        this.acadYear = acadYear;
        this.moduleCode = moduleCode;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> aClass) {
        return (T) new ModuleDetailViewModel(moduleRepository, acadYear, moduleCode);
    }
}
