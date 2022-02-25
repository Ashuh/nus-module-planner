package com.ashuh.nusmoduleplanner.data.source;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.data.source.remote.nusmods.NusModsService;
import com.ashuh.nusmoduleplanner.data.source.remote.nusmods.NusModsServiceGenerator;
import com.ashuh.nusmoduleplanner.data.source.remote.nusmods.ModuleDeserializer;
import com.ashuh.nusmoduleplanner.data.model.nusmods.ModuleCondensed;
import com.ashuh.nusmoduleplanner.data.model.nusmods.ModuleDetail;
import com.ashuh.nusmoduleplanner.util.AcademicYear;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleDataSource {
    private static final NusModsService MODULE_API =
            NusModsServiceGenerator
                    .getRetrofitInstance(ModuleDetail.class, new ModuleDeserializer())
                    .create(NusModsService.class);

    private static final NusModsService MODULE_INFORMATION_API =
            NusModsServiceGenerator.getRetrofitInstance().create(NusModsService.class);

    public void getModules(AcademicYear acadYear,
                           ResponseListener<List<ModuleCondensed>> listener) {
        Call<List<ModuleCondensed>> call = MODULE_INFORMATION_API
                .getModuleInfo(acadYear.toString());
        call.enqueue(new NusModsCallback<>(listener));
    }

    public void getModule(AcademicYear acadYear, String moduleCode,
                          ResponseListener<ModuleDetail> listener) {
        Call<ModuleDetail> call = MODULE_API.getModule(acadYear.toString(), moduleCode);
        call.enqueue(new NusModsCallback<>(listener));
    }

    public interface ResponseListener<T> {
        void onError(String message);

        void onResponse(T response);
    }

    private static class NusModsCallback<T> implements Callback<T> {
        ResponseListener<T> listener;

        public NusModsCallback(ResponseListener<T> listener) {
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
