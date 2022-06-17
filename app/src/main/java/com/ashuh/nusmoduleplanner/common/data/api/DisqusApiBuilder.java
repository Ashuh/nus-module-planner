package com.ashuh.nusmoduleplanner.common.data.api;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisqusApiBuilder {
    private static final String BASE_URL = "https://disqus.com/api/3.0/";
    private static DisqusApiBuilder instance;
    private final DisqusApi api;

    private DisqusApiBuilder() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DisqusApi.class);
    }

    @NonNull
    public static DisqusApiBuilder getInstance() {
        if (instance == null) {
            instance = new DisqusApiBuilder();
        }
        return instance;
    }

    @NonNull
    public DisqusApi getApi() {
        return api;
    }
}
