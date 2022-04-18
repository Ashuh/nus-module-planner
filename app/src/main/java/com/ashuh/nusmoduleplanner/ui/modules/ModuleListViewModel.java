package com.ashuh.nusmoduleplanner.ui.modules;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.data.model.nusmods.AcademicYear;
import com.ashuh.nusmoduleplanner.data.source.nusmods.ModulesRepository;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.ModuleInformation;

import java.util.List;

public class ModuleListViewModel extends ViewModel {
    private final LiveData<List<ModuleInformation>> data;

    public ModuleListViewModel() {
        data = ModulesRepository.getInstance().getModules(AcademicYear.getCurrent());
    }

    public LiveData<List<ModuleInformation>> getModuleListObservable() {
        return data;
    }
}