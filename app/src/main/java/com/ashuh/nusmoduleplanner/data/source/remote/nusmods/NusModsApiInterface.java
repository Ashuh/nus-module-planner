package com.ashuh.nusmoduleplanner.data.source.remote.nusmods;

import com.ashuh.nusmoduleplanner.data.model.nusmods.ModuleCondensed;
import com.ashuh.nusmoduleplanner.data.model.nusmods.ModuleDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NusModsApiInterface {
    @GET("{acadYear}/moduleInfo.json")
    Call<List<ModuleCondensed>> getModuleInfo(@Path("acadYear") String acadYear);

    @GET("{acadYear}/modules/{moduleCode}.json")
    Call<ModuleDetail> getModule(@Path("acadYear") String acadYear,
                                 @Path("moduleCode") String moduleCode);
}
