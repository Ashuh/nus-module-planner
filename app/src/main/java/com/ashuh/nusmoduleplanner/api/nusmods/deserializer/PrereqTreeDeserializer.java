package com.ashuh.nusmoduleplanner.api.nusmods.deserializer;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree.And;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree.LogicalPrerequisite;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree.Or;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree.PrereqTree;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree.Prerequisite;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree.SinglePrerequisite;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrereqTreeDeserializer implements JsonDeserializer<PrereqTree> {

    @Override
    public PrereqTree deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            SinglePrerequisite prerequisite =
                    deserializeJsonPrimitive(json.getAsJsonPrimitive());
            return new PrereqTree(prerequisite);
        } else if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            LogicalPrerequisite prerequisite = deserializeJsonObject(jsonObject);
            return new PrereqTree(prerequisite);
        }

        throw new JsonParseException("json is neither a JsonPrimitive nor a JsonObject: " + json);
    }

    private SinglePrerequisite deserializeJsonPrimitive(JsonPrimitive jsonPrimitive) {
        return new SinglePrerequisite(jsonPrimitive.getAsString());
    }

    private LogicalPrerequisite deserializeJsonObject(JsonObject jsonObject) {
        if (jsonObject.has("and")) {
            List<Prerequisite> prerequisites =
                    deserializeJsonArray(jsonObject.get("and").getAsJsonArray());
            return new And(prerequisites);
        } else if (jsonObject.has("or")) {
            List<Prerequisite> prerequisites =
                    deserializeJsonArray(jsonObject.get("or").getAsJsonArray());
            return new Or(prerequisites);
        }

        throw new JsonParseException("Invalid jsonObject: " + jsonObject);
    }

    private List<Prerequisite> deserializeJsonArray(JsonArray jsonArray) {
        List<Prerequisite> prerequisites = new ArrayList<>();

        for (JsonElement e : jsonArray) {
            if (e.isJsonObject()) {
                prerequisites.add(deserializeJsonObject(e.getAsJsonObject()));
            } else {
                prerequisites.add(deserializeJsonPrimitive(e.getAsJsonPrimitive()));
            }
        }

        return prerequisites;
    }
}
