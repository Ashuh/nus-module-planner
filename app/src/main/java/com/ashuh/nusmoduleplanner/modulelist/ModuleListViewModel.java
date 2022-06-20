package com.ashuh.nusmoduleplanner.modulelist;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleInfo;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;

import java.util.List;

public class ModuleListViewModel extends ViewModel {
    @NonNull
    private final ModuleRepository moduleRepository;
    private final LiveData<List<ModuleInfo>> data;

    public ModuleListViewModel(@NonNull ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
        data = moduleRepository.getAllModuleInfo(AcademicYear.getCurrent().toString());
    }

    public LiveData<List<ModuleInfo>> getModuleListObservable() {
        return data;
    }
}
