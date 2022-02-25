package com.ashuh.nusmoduleplanner.data.source;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ashuh.nusmoduleplanner.data.model.nusmods.ModuleCondensed;
import com.ashuh.nusmoduleplanner.data.model.nusmods.ModuleDetail;
import com.ashuh.nusmoduleplanner.util.AcademicYear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModulesRepository {
    private static ModulesRepository instance;
    private final MutableLiveData<List<ModuleCondensed>> moduleInfoRequests =
            new MutableLiveData<>();
    private final Map<String, MutableLiveData<ModuleDetail>> moduleDetailRequests = new HashMap<>();
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

    public LiveData<List<ModuleCondensed>> getModules(AcademicYear acadYear) {
        if (moduleInfoRequests.getValue() == null) {

            source.getModules(acadYear,
                    new ModuleDataSource.ResponseListener<List<ModuleCondensed>>() {
                        @Override
                        public void onError(String message) {
                            Log.e("getModules", message);
                        }

                        @Override
                        public void onResponse(List<ModuleCondensed> response) {
                            List<ModuleCondensed> filtered = new ArrayList<>();

                            for (int i = 0; i < response.size(); i++) {
                                if (!response.get(i).getCondensedSemesters().isEmpty()) {
                                    filtered.add(response.get(i));
                                }
                            }
                            moduleInfoRequests.setValue(filtered);
                        }
                    });
        }

        return moduleInfoRequests;
    }

    public LiveData<ModuleDetail> getModuleDetail(AcademicYear acadYear, String moduleCode) {
        if (moduleDetailRequests.containsKey(moduleCode)) {
            return moduleDetailRequests.get(moduleCode);
        }

        final MutableLiveData<ModuleDetail> data = new MutableLiveData<>();
        source.getModule(acadYear, moduleCode,
                new ModuleDataSource.ResponseListener<ModuleDetail>() {
                    @Override
                    public void onError(String message) {
                        Log.e("getModule", message);
                    }

                    @Override
                    public void onResponse(ModuleDetail response) {
                        data.setValue(response);
                        moduleDetailRequests.put(moduleCode, data);
                    }
                });

        return data;
    }

    public boolean hasModule(AcademicYear acadYear, String moduleCode) {
        for (ModuleCondensed m : getModules(acadYear).getValue()) {
            if (m.getModuleCode().equals(moduleCode)) {
                return true;
            }
        }

        return false;
    }
}
