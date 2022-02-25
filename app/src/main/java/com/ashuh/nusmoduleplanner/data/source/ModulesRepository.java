package com.ashuh.nusmoduleplanner.data.source;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ashuh.nusmoduleplanner.data.model.nusmods.ModuleCondensed;
import com.ashuh.nusmoduleplanner.data.model.nusmods.ModuleDetail;
import com.ashuh.nusmoduleplanner.data.source.remote.nusmods.ModuleDeserializer;
import com.ashuh.nusmoduleplanner.data.source.remote.nusmods.NusModsApiInterface;
import com.ashuh.nusmoduleplanner.data.source.remote.nusmods.NusModsApiInterfaceBuilder;
import com.ashuh.nusmoduleplanner.util.AcademicYear;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModulesRepository {
    private static final String TAG = "ModulesRepository";

    private static ModulesRepository instance;
    private final MutableLiveData<List<ModuleCondensed>> moduleInfoCache = new MutableLiveData<>();
    private final Map<String, MutableLiveData<ModuleDetail>> moduleDetailCache = new HashMap<>();

    public static ModulesRepository getInstance() {
        if (instance == null) {
            instance = new ModulesRepository();
        }
        return instance;
    }

    public LiveData<List<ModuleCondensed>> getModules(AcademicYear acadYear) {
        if (moduleInfoCache.getValue() != null) {
            return moduleInfoCache;
        }

        MutableLiveData<List<ModuleCondensed>> modules = new MutableLiveData<>();
        NusModsApiInterface api =
                NusModsApiInterfaceBuilder.getApiInterface().create(NusModsApiInterface.class);

        Call<List<ModuleCondensed>> call = api.getModuleInfo(acadYear.toString());
        call.enqueue(new Callback<List<ModuleCondensed>>() {
            @Override
            public void onResponse(@NonNull Call<List<ModuleCondensed>> call,
                                   @NonNull Response<List<ModuleCondensed>> response) {
                modules.setValue(response.body());
                moduleInfoCache.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<ModuleCondensed>> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });

        return modules;
    }

    public LiveData<ModuleDetail> getModuleDetail(AcademicYear acadYear, String moduleCode) {
        if (moduleDetailCache.containsKey(moduleCode)) {
            return moduleDetailCache.get(moduleCode);
        }

        MutableLiveData<ModuleDetail> module = new MutableLiveData<>();
        NusModsApiInterface api = NusModsApiInterfaceBuilder
                .getApiInterface(ModuleDetail.class, new ModuleDeserializer())
                .create(NusModsApiInterface.class);

        Call<ModuleDetail> call = api.getModule(acadYear.toString(), moduleCode);
        call.enqueue(new Callback<ModuleDetail>() {
            @Override
            public void onResponse(@NonNull Call<ModuleDetail> call,
                                   @NonNull Response<ModuleDetail> response) {
                module.setValue(response.body());
                moduleDetailCache.put(moduleCode, module);
            }

            @Override
            public void onFailure(@NonNull Call<ModuleDetail> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });

        return module;
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
