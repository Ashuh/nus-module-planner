package com.ashuh.nusmoduleplanner.data.model.typeadapter.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeDeserializer implements JsonDeserializer<ZonedDateTime> {

    private final DateTimeFormatter formatter;

    public ZonedDateTimeDeserializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public ZonedDateTime deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {
        return ZonedDateTime.parse(json.getAsString(), formatter);
    }
}
