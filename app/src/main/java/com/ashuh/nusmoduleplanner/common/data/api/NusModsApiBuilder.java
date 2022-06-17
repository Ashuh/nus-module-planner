package com.ashuh.nusmoduleplanner.common.data.api;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NusModsApiBuilder {
    private static final String BASE_URL = "https://api.nusmods.com/v2/";
    private static NusModsApiBuilder instance;
    private final NusModsApi api;

    private NusModsApiBuilder() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NusModsApi.class);
    }

    @NonNull
    public static NusModsApiBuilder getInstance() {
        if (instance == null) {
            instance = new NusModsApiBuilder();
        }
        return instance;
    }

    @NonNull
    public NusModsApi getApi() {
        return api;
    }
}
