package com.ashuh.nusmoduleplanner.ui.moduledetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.data.model.nusmods.AcademicYear;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.source.nusmods.ModulesRepository;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.Module;
import com.ashuh.nusmoduleplanner.data.source.timetable.TimetableDataSource;

public class ModuleDetailViewModel extends ViewModel {

    private final TimetableDataSource timetableDataSource;
    private final LiveData<Module> module;

    public ModuleDetailViewModel(TimetableDataSource timetableDataSource,
                                 AcademicYear acadYear, String moduleCode) {
        this.timetableDataSource = timetableDataSource;
        module = ModulesRepository.getInstance().getModule(acadYear, moduleCode);
    }

    public LiveData<Module> getModuleObservable() {
        return module;
    }

    public void addAssignedModule(AssignedModule module) {
        timetableDataSource.insert(module);
    }
}
