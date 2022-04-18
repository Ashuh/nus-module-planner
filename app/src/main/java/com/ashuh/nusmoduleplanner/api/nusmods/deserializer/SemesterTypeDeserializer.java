package com.ashuh.nusmoduleplanner.api.nusmods.deserializer;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class SemesterTypeDeserializer implements JsonDeserializer<SemesterType> {

    @Override
    public SemesterType deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
        return SemesterType.fromId(json.getAsInt());
    }
}
