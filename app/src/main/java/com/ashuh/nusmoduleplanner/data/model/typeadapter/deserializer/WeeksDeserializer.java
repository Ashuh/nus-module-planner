package com.ashuh.nusmoduleplanner.data.model.typeadapter.deserializer;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.weeks.WeekRange;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.weeks.Weeks;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeeksDeserializer implements JsonDeserializer<Weeks> {

    @Override
    public Weeks deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        if (json.isJsonArray()) {
            List<Integer> weeks = deserializeWeeks(json.getAsJsonArray());
            return new Weeks(weeks);
        } else {
            JsonObject jsonObject = json.getAsJsonObject();

            String start = jsonObject.get("start").getAsString();
            String end = jsonObject.get("end").getAsString();
            int weekInterval = jsonObject.has("weekInterval") ?
                    jsonObject.get("weekInterval").getAsInt() : 0;
            List<Integer> weeks = jsonObject.has("weeks") ?
                    deserializeWeeks(jsonObject.get("weeks").getAsJsonArray()) :
                    Collections.emptyList();

            return new WeekRange(start, end, weekInterval, weeks);
        }
    }

    private List<Integer> deserializeWeeks(JsonArray jsonArray) {
        List<Integer> weeks = new ArrayList<>();

        for (JsonElement e : jsonArray.getAsJsonArray()) {
            weeks.add(e.getAsInt());
        }

        return weeks;
    }
}
