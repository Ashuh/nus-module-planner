package com.ashuh.nusmoduleplanner.timetable.presentation;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.GetColorSchemeUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.UpdateColorUseCase;

public class ColorSelectViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final ModuleRepository moduleRepository;

    public ColorSelectViewModelFactory(@NonNull ModuleRepository moduleRepository) {
        this.moduleRepository = requireNonNull(moduleRepository);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        GetColorSchemeUseCase getColorSchemeUseCase = new GetColorSchemeUseCase();
        UpdateColorUseCase updateColorUseCase = new UpdateColorUseCase(moduleRepository);
        return (T) new ColorSelectViewModel(getColorSchemeUseCase, updateColorUseCase);
    }
}
