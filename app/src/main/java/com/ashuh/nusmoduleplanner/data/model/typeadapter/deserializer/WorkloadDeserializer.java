package com.ashuh.nusmoduleplanner.data.model.typeadapter.deserializer;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.workload.Workload;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WorkloadDeserializer implements JsonDeserializer<Workload> {

    @Override
    public Workload deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        if (json.isJsonArray()) {
            List<Double> workload = new ArrayList<>();

            for (JsonElement e : json.getAsJsonArray()) {
                workload.add(e.getAsDouble());
            }

            return new Workload(workload);
        } else {
            return new Workload(json.getAsString());
        }
    }
}
