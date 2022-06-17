package com.ashuh.nusmoduleplanner.common.data.api;

import com.ashuh.nusmoduleplanner.common.data.api.model.module.ApiModule;
import com.ashuh.nusmoduleplanner.common.data.api.model.module.ApiModuleInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NusModsApi {
    String PARAMETER_ACADEMIC_YEAR = "acadYear";
    String PARAMETER_MODULE_CODE = "moduleCode";

    String ENDPOINT_MODULE_INFO = "{"
            + PARAMETER_ACADEMIC_YEAR
            + "}/moduleInfo.json";
    String ENDPOINT_MODULE = "{"
            + PARAMETER_ACADEMIC_YEAR
            + "}/modules/{"
            + PARAMETER_MODULE_CODE
            + "}.json";

    @GET(ENDPOINT_MODULE_INFO)
    Call<List<ApiModuleInfo>> getModuleInfo(@Path(PARAMETER_ACADEMIC_YEAR) String acadYear);

    @GET(ENDPOINT_MODULE)
    Call<ApiModule> getModule(@Path(PARAMETER_ACADEMIC_YEAR) String acadYear,
                              @Path(PARAMETER_MODULE_CODE) String moduleCode);
}
