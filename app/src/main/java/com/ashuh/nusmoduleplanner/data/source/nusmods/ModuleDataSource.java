package com.ashuh.nusmoduleplanner.data.source.nusmods;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.api.nusmods.NusModsApiInterface;
import com.ashuh.nusmoduleplanner.api.nusmods.NusModsApiInterfaceBuilder;
import com.ashuh.nusmoduleplanner.data.model.nusmods.AcademicYear;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.Module;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.ModuleInformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleDataSource {
    private static final NusModsApiInterface MODULE_API =
            NusModsApiInterfaceBuilder.getRetrofitInstance().create(NusModsApiInterface.class);

    private static final NusModsApiInterface MODULE_INFORMATION_API =
            NusModsApiInterfaceBuilder.getRetrofitInstance().create(NusModsApiInterface.class);

    public void getModules(AcademicYear acadYear,
                           ResponseListener<List<ModuleInformation>> listener) {
        Call<List<ModuleInformation>> call = MODULE_INFORMATION_API
                .getModuleInfo(acadYear.toString());
        call.enqueue(new NusModsCallback<>(listener));
    }

    public void getModule(AcademicYear acadYear, String moduleCode,
                          ResponseListener<Module> listener) {
        Call<Module> call = MODULE_API.getModule(acadYear.toString(), moduleCode);
        call.enqueue(new NusModsCallback<>(listener));
    }

    public interface ResponseListener<T> {
        void onError(String message);

        void onResponse(T response);
    }

    private static class NusModsCallback<T> implements Callback<T> {
        final ResponseListener<T> listener;

        NusModsCallback(ResponseListener<T> listener) {
            this.listener = listener;
        }

        @Override
        public void onResponse(@NonNull Call<T> call, Response<T> response) {
            listener.onResponse(response.body());
        }

        @Override
        public void onFailure(@NonNull Call<T> call, Throwable t) {
            listener.onError(t.toString());
        }
    }
}
