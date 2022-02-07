package com.ashuh.nusmoduleplanner.api.deserializer;

import com.ashuh.nusmoduleplanner.data.module.ModuleDetail;
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

public class ModuleDeserializer extends OptionalDeserializer implements JsonDeserializer<ModuleDetail> {
    private static final Gson SEMESTER_DESERIALIZER = new GsonBuilder()
            .registerTypeAdapter(SemesterDetail.class, new SemesterDataDeserializer())
            .create();

    @Override
    public ModuleDetail deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        String moduleCode = jsonObject.get("moduleCode").getAsString();
        String title = jsonObject.get("title").getAsString();
        String department = jsonObject.get("department").getAsString();
        double moduleCredit = jsonObject.get("moduleCredit").getAsDouble();
        String faculty = jsonObject.get("faculty").getAsString();
        String description = jsonObject.get("description").getAsString();
        String prerequisite = getOptionalString(jsonObject, "prerequisite");
        String preclusion = getOptionalString(jsonObject, "preclusion");
        String corequisite = getOptionalString(jsonObject, "corequisite");

        List<SemesterDetail> semesterDetails = new ArrayList<>();
        for (JsonElement element : jsonObject.get("semesterData").getAsJsonArray()) {
            SemesterDetail semesterDetail =
                    SEMESTER_DESERIALIZER.fromJson(element.getAsJsonObject(), SemesterDetail.class);
            semesterDetails.add(semesterDetail);
        }

        return new ModuleDetail(moduleCode, title, department, faculty, moduleCredit,
                semesterDetails,
                description, prerequisite, preclusion, corequisite);
    }
}
