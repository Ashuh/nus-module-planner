package com.ashuh.nusmoduleplanner.ui.moduledetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.util.AcademicYear;
import com.ashuh.nusmoduleplanner.data.source.ModulesRepository;
import com.ashuh.nusmoduleplanner.data.model.module.ModuleDetail;

public class ModuleDetailViewModel extends ViewModel {
    private final LiveData<ModuleDetail> module;

    public ModuleDetailViewModel(AcademicYear acadYear, String moduleCode) {
        module = ModulesRepository.getInstance().getModuleDetail(acadYear, moduleCode);
    }

    public LiveData<ModuleDetail> getModuleObservable() {
        return module;
    }
}
