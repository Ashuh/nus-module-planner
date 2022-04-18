package com.ashuh.nusmoduleplanner.api.nusmods;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.Module;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.ModuleInformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NusModsApiInterface {

    @GET("{acadYear}/moduleInfo.json")
    Call<List<ModuleInformation>> getModuleInfo(@Path("acadYear") String acadYear);

    @GET("{acadYear}/modules/{moduleCode}.json")
    Call<Module> getModule(@Path("acadYear") String acadYear,
                           @Path("moduleCode") String moduleCode);
}
