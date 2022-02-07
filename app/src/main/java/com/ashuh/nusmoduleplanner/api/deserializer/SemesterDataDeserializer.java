package com.ashuh.nusmoduleplanner.api.deserializer;

import com.ashuh.nusmoduleplanner.data.module.Lesson;
import com.ashuh.nusmoduleplanner.data.module.SemesterDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SemesterDataDeserializer extends OptionalDeserializer implements JsonDeserializer<SemesterDetail> {
    private static final Gson LESSON_DESERIALIZER = new GsonBuilder()
            .registerTypeAdapter(Lesson.class, new LessonDeserializer())
            .create();

    @Override
    public SemesterDetail deserialize(JsonElement json, Type typeOfT,
                                      JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        int semester = jsonObject.get("semester").getAsInt();
        String examDate = getOptionalString(jsonObject, "examDate");
        int examDuration = getOptionalInt(jsonObject, "examDuration");

        List<Lesson> timetable = new ArrayList<>();
        for (JsonElement element : jsonObject.get("timetable").getAsJsonArray()) {
            Lesson lesson = LESSON_DESERIALIZER.fromJson(element.getAsJsonObject(), Lesson.class);
            timetable.add(lesson);
        }

        return new SemesterDetail(semester, examDate, examDuration, timetable);
    }
}
