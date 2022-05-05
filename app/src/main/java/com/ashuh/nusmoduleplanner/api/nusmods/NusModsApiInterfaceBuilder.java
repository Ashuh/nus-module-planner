package com.ashuh.nusmoduleplanner.api.nusmods;

import com.ashuh.nusmoduleplanner.data.model.typeadapter.deserializer.PrereqTreeDeserializer;
import com.ashuh.nusmoduleplanner.data.model.typeadapter.deserializer.SemesterTypeDeserializer;
import com.ashuh.nusmoduleplanner.data.model.typeadapter.deserializer.WeeksDeserializer;
import com.ashuh.nusmoduleplanner.data.model.typeadapter.deserializer.WorkloadDeserializer;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree.PrereqTree;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.weeks.Weeks;
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
                .registerTypeAdapter(Weeks.class, new WeeksDeserializer())
                .registerTypeAdapter(Workload.class, new WorkloadDeserializer());
        Gson gson = gsonBuilder.create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
