package com.ashuh.nusmoduleplanner.ui.modules;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.util.AcademicYear;
import com.ashuh.nusmoduleplanner.data.source.ModulesRepository;
import com.ashuh.nusmoduleplanner.data.model.module.ModuleCondensed;

import java.util.List;

public class ModuleListViewModel extends ViewModel {
    private final LiveData<List<ModuleCondensed>> data;

    public ModuleListViewModel() {
        data = ModulesRepository.getInstance().getModules(AcademicYear.getCurrent());
    }

    public LiveData<List<ModuleCondensed>> getModuleListObservable() {
        return data;
    }
}