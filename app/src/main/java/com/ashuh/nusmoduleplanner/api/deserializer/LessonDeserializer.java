package com.ashuh.nusmoduleplanner.api.deserializer;

import com.ashuh.nusmoduleplanner.data.module.Lesson;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LessonDeserializer implements JsonDeserializer<Lesson> {

    @Override
    public Lesson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String classNo = jsonObject.get("classNo").getAsString();
        String startTime = jsonObject.get("startTime").getAsString();
        String endTime = jsonObject.get("endTime").getAsString();
        String venue = jsonObject.get("venue").getAsString();
        String day = jsonObject.get("day").getAsString();
        String lessonType = jsonObject.get("lessonType").getAsString();
        int size = jsonObject.get("size").getAsInt();

        JsonElement jsonWeeks = jsonObject.get("weeks");
        if (!jsonWeeks.isJsonArray()) {
            jsonWeeks = jsonWeeks.getAsJsonObject().get("weeks");
        }

        Type listType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        List<Integer> weeks = new Gson().fromJson(jsonWeeks, listType);

        return new Lesson(classNo, startTime, endTime, venue, day, lessonType, size,
                weeks);
    }
}
