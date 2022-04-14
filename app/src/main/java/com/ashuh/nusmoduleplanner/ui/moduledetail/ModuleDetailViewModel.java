package com.ashuh.nusmoduleplanner.ui.moduledetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.data.model.nusmods.AcademicYear;
import com.ashuh.nusmoduleplanner.data.source.nusmods.ModulesRepository;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.Module;

public class ModuleDetailViewModel extends ViewModel {
    private final LiveData<Module> module;

    public ModuleDetailViewModel(AcademicYear acadYear, String moduleCode) {
        module = ModulesRepository.getInstance().getModule(acadYear, moduleCode);
    }

    public LiveData<Module> getModuleObservable() {
        return module;
    }
}
