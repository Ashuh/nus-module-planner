package com.ashuh.nusmoduleplanner.api;

import com.ashuh.nusmoduleplanner.data.module.ModuleCondensed;
import com.ashuh.nusmoduleplanner.data.module.ModuleDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NusModsService {
    @GET("{acadYear}/moduleInfo.json")
    Call<List<ModuleCondensed>> getModuleInfo(@Path("acadYear") String acadYear);

    @GET("{acadYear}/modules/{moduleCode}.json")
    Call<ModuleDetail> getModule(@Path("acadYear") String acadYear,
                                 @Path("moduleCode") String moduleCode);
}
