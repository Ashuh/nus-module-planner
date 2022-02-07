package com.ashuh.nusmoduleplanner.ui.moduledetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.data.AcademicYear;
import com.ashuh.nusmoduleplanner.data.ModulesRepository;
import com.ashuh.nusmoduleplanner.data.module.ModuleDetail;

public class ModuleDetailViewModel extends ViewModel {
    private final LiveData<ModuleDetail> module;

    public ModuleDetailViewModel(AcademicYear acadYear, String moduleCode) {
        module = ModulesRepository.getInstance().getModule(acadYear, moduleCode);
    }

    public LiveData<ModuleDetail> getModuleObservable() {
        return module;
    }
}
