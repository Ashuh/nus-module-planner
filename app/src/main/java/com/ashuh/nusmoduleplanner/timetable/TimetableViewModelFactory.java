package com.ashuh.nusmoduleplanner.timetable;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;

import org.jetbrains.annotations.NotNull;


public class TimetableViewModelFactory implements ViewModelProvider.Factory {
    private final ModuleRepository moduleRepository;
    private final Semester semester;

    public TimetableViewModelFactory(ModuleRepository moduleRepository, Semester sem) {
        this.moduleRepository = moduleRepository;
        this.semester = sem;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> aClass) {
        return (T) new TimetableViewModel(moduleRepository, semester);
    }
}
