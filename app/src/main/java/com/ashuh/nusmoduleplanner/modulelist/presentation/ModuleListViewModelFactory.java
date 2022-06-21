package com.ashuh.nusmoduleplanner.modulelist.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.modulelist.domain.usecase.GetModuleInfoUseCase;

public class ModuleListViewModelFactory implements ViewModelProvider.Factory {
    private final ModuleRepository moduleRepository;

    public ModuleListViewModelFactory(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        GetModuleInfoUseCase getModuleInfoUseCase = new GetModuleInfoUseCase(moduleRepository);
        return (T) new ModuleListViewModel(getModuleInfoUseCase);
    }
}
