package com.ashuh.nusmoduleplanner.ui.timetable;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.source.timetable.TimetableDataSource;

import org.jetbrains.annotations.NotNull;


public class TimetableViewModelFactory implements ViewModelProvider.Factory {

    private final TimetableDataSource dataSource;
    private final SemesterType sem;

    public TimetableViewModelFactory(TimetableDataSource dataSource, SemesterType sem) {
        this.dataSource = dataSource;
        this.sem = sem;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> aClass) {
        return (T) new TimetableViewModel(dataSource, sem);
    }
}
