package com.ashuh.nusmoduleplanner.common.data.remote.source;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.ashuh.nusmoduleplanner.common.data.remote.api.NusModsApi;
import com.ashuh.nusmoduleplanner.common.data.remote.model.module.ApiModule;
import com.ashuh.nusmoduleplanner.common.data.remote.model.module.ApiModuleInfo;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleInfo;

import java.util.List;
import java.util.stream.Collectors;

public class ModuleRemoteDataSource {
    @NonNull
    private final NusModsApi nusModsApi;

    public ModuleRemoteDataSource(@NonNull NusModsApi nusModsApi) {
        this.nusModsApi = requireNonNull(nusModsApi);
    }

    @NonNull
    public LiveData<List<ModuleInfo>> getAllModuleInfo(@NonNull String acadYear) {
        MutableLiveData<List<ApiModuleInfo>> observableApiModuleInfo = new MutableLiveData<>();
        nusModsApi.getModuleInfo(acadYear).enqueue(new LiveDataCallback<>(observableApiModuleInfo));
        return Transformations.map(observableApiModuleInfo, apiModuleInfoList ->
                apiModuleInfoList.stream()
                        .map(ApiModuleInfo::toDomain)
                        .collect(Collectors.toList())
        );
    }

    @NonNull
    public LiveData<Module> getModule(@NonNull String acadYear, @NonNull String moduleCode) {
        MutableLiveData<ApiModule> observableApiModule = new MutableLiveData<>();
        nusModsApi.getModule(acadYear, moduleCode)
                .enqueue(new LiveDataCallback<>(observableApiModule));
        return Transformations.map(observableApiModule, ApiModule::toDomain);
    }
}
