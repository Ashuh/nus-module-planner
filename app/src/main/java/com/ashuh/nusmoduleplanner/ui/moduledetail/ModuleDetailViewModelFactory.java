package com.ashuh.nusmoduleplanner.ui.moduledetail;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.data.AcademicYear;

import org.jetbrains.annotations.NotNull;

public class ModuleDetailViewModelFactory implements ViewModelProvider.Factory {
    private final AcademicYear acadYear;
    private final String moduleCode;

    public ModuleDetailViewModelFactory(AcademicYear acadYear, String moduleCode) {
        this.acadYear = acadYear;
        this.moduleCode = moduleCode;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> aClass) {
        return (T) new ModuleDetailViewModel(acadYear, moduleCode);
    }
}
