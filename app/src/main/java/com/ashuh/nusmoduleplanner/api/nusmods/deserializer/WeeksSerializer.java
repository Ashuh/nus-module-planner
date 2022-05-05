package com.ashuh.nusmoduleplanner.api.nusmods.deserializer;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.weeks.WeekRange;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.weeks.Weeks;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class WeeksSerializer implements JsonSerializer<Weeks> {

    @Override
    public JsonElement serialize(Weeks src, Type typeOfSrc, JsonSerializationContext context) {
        if (src instanceof WeekRange) {
            return context.serialize(src);
        } else {
            return context.serialize(src.getWeeks());
        }
    }
}
