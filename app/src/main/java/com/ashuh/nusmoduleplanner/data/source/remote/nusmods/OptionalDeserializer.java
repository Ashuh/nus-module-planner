package com.ashuh.nusmoduleplanner.data.source.remote.nusmods;

import com.google.gson.JsonObject;

public abstract class OptionalDeserializer {
    protected String getOptionalString(JsonObject jsonObject, String name) {
        return jsonObject.has(name) ? jsonObject.get(name).getAsString() : null;
    }

    protected int getOptionalInt(JsonObject jsonObject, String name) {
        return jsonObject.has(name) ? jsonObject.get(name).getAsInt() : 0;
    }
}
