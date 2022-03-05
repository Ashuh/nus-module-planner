package com.ashuh.nusmoduleplanner.ui.timetable;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.data.model.module.Semester;

import org.jetbrains.annotations.NotNull;


public class TimetableViewModelFactory implements ViewModelProvider.Factory {
    private final Semester sem;

    public TimetableViewModelFactory(Semester sem) {
        this.sem = sem;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> aClass) {
        return (T) new TimetableViewModel(sem);
    }
}
