package com.ashuh.nusmoduleplanner.data.source.nusmods;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ashuh.nusmoduleplanner.data.model.nusmods.AcademicYear;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.Module;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.ModuleInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModulesRepository {
    private static ModulesRepository instance;
    private final MutableLiveData<List<ModuleInformation>> moduleInfoRequests =
            new MutableLiveData<>();
    private final Map<String, MutableLiveData<Module>> moduleDetailRequests = new HashMap<>();
    private final ModuleDataSource source;

    private ModulesRepository() {
        source = new ModuleDataSource();
    }

    public static ModulesRepository getInstance() {
        if (instance == null) {
            instance = new ModulesRepository();
        }
        return instance;
    }

    public LiveData<Module> getModule(AcademicYear acadYear, String moduleCode) {
        if (moduleDetailRequests.containsKey(moduleCode)) {
            return moduleDetailRequests.get(moduleCode);
        }

        final MutableLiveData<Module> data = new MutableLiveData<>();
        source.getModule(acadYear, moduleCode,
                new ModuleDataSource.ResponseListener<Module>() {
                    @Override
                    public void onError(String message) {
                        Log.e("getModule", message);
                    }

                    @Override
                    public void onResponse(Module response) {
                        data.setValue(response);
                        moduleDetailRequests.put(moduleCode, data);
                    }
                });

        return data;
    }

    public boolean hasModule(AcademicYear acadYear, String moduleCode) {
        for (ModuleInformation m : getModules(acadYear).getValue()) {
            if (m.getModuleCode().equals(moduleCode)) {
                return true;
            }
        }

        return false;
    }

    public LiveData<List<ModuleInformation>> getModules(AcademicYear acadYear) {
        if (moduleInfoRequests.getValue() == null) {

            source.getModules(acadYear,
                    new ModuleDataSource.ResponseListener<List<ModuleInformation>>() {
                        @Override
                        public void onError(String message) {
                            Log.e("getModules", message);
                        }

                        @Override
                        public void onResponse(List<ModuleInformation> response) {
                            List<ModuleInformation> filtered = new ArrayList<>();

                            for (int i = 0; i < response.size(); i++) {
                                if (!response.get(i).getSemesterData().isEmpty()) {
                                    filtered.add(response.get(i));
                                }
                            }
                            moduleInfoRequests.setValue(filtered);
                        }
                    });
        }

        return moduleInfoRequests;
    }
}
