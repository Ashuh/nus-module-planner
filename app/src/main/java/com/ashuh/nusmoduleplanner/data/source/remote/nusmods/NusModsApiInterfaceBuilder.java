package com.ashuh.nusmoduleplanner.data.source.remote.nusmods;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ashuh.nusmoduleplanner.data.model.nusmods.Weeks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NusModsApiInterfaceBuilder {
    private static final String BASE_URL = "https://api.nusmods.com/v2/";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Retrofit getApiInterface() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Weeks.class,
                (JsonDeserializer<Weeks>) (json, typeOfT, context) -> {
                    if (!json.isJsonArray()) {
                        json = json.getAsJsonObject().get("weeks");
                    }

                    Type listType = new TypeToken<ArrayList<Integer>>() {
                    }.getType();
                    List<Integer> weeks = new Gson().fromJson(json, listType);
                    return new Weeks(weeks);
                })
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
