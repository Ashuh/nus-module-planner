package com.ashuh.nusmoduleplanner.api.disqus;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisqusApiInterfaceBuilder {

    private static final String BASE_URL = "https://disqus.com/api/3.0/";
    private static DisqusApiInterface instance;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static DisqusApiInterface getApiInterface() {
        if (instance == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .withZone(ZoneId.of("UTC"));

            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime
                            .parse(json.getAsString(), formatter)).create();

            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(DisqusApiInterface.class);
        }

        return instance;
    }
}
