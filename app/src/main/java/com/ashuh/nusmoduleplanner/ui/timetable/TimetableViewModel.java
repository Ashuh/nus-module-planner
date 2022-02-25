package com.ashuh.nusmoduleplanner.ui.timetable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.data.model.nusmods.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.source.TimetableDataSource;

import java.util.List;

public class TimetableViewModel extends ViewModel {
    private final LiveData<List<AssignedModule>> data;

    public TimetableViewModel(SemesterType sem) {
        data = TimetableDataSource.getInstance().getSemesterAssignedModules(sem);
    }

    public LiveData<List<AssignedModule>> getTimetableEntriesObservable() {
        return data;
    }
}