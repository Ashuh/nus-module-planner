package com.ashuh.nusmoduleplanner.api.nusmods;

import com.ashuh.nusmoduleplanner.api.nusmods.deserializer.PrereqTreeDeserializer;
import com.ashuh.nusmoduleplanner.api.nusmods.deserializer.SemesterTypeDeserializer;
import com.ashuh.nusmoduleplanner.api.nusmods.deserializer.WeekRangeDeserializer;
import com.ashuh.nusmoduleplanner.api.nusmods.deserializer.WorkloadDeserializer;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree.PrereqTree;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.weekrange.WeekRange;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.workload.Workload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NusModsApiInterfaceBuilder {
    private static final String BASE_URL = "https://api.nusmods.com/v2/";

    public static Retrofit getRetrofitInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(PrereqTree.class, new PrereqTreeDeserializer())
                .registerTypeAdapter(SemesterType.class, new SemesterTypeDeserializer())
                .registerTypeAdapter(WeekRange.class, new WeekRangeDeserializer())
                .registerTypeAdapter(Workload.class, new WorkloadDeserializer());
        Gson gson = gsonBuilder.create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
