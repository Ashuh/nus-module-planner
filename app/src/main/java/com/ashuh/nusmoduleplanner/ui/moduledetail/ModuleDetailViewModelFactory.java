package com.ashuh.nusmoduleplanner.ui.moduledetail;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.data.model.nusmods.AcademicYear;
import com.ashuh.nusmoduleplanner.data.source.timetable.TimetableDataSource;

import org.jetbrains.annotations.NotNull;

public class ModuleDetailViewModelFactory implements ViewModelProvider.Factory {

    private final AcademicYear acadYear;
    private final String moduleCode;
    private final TimetableDataSource timetableDataSource;

    public ModuleDetailViewModelFactory(TimetableDataSource timetableDataSource,
                                        AcademicYear acadYear, String moduleCode) {
        this.timetableDataSource = timetableDataSource;
        this.acadYear = acadYear;
        this.moduleCode = moduleCode;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> aClass) {
        return (T) new ModuleDetailViewModel(timetableDataSource, acadYear, moduleCode);
    }
}
