package com.ashuh.nusmoduleplanner.api.nusmods.deserializer;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.weekrange.WeekRange;
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

public class WeekRangeDeserializer implements JsonDeserializer<WeekRange> {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public WeekRange deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        if (json.isJsonArray()) {
            List<Integer> weeks = deserializeWeeks(json.getAsJsonArray());
            return new WeekRange(weeks);
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
