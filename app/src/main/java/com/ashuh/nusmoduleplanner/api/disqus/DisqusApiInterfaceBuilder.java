package com.ashuh.nusmoduleplanner.api.disqus;


import com.ashuh.nusmoduleplanner.data.model.typeadapter.deserializer.ZonedDateTimeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisqusApiInterfaceBuilder {

    private static final String BASE_URL = "https://disqus.com/api/3.0/";
    private static final DateTimeFormatter DISQUS_DATE_TIME_FORMATTER
            = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of("UTC"));

    private static DisqusApiInterface instance;

    public static DisqusApiInterface getApiInterface() {
        if (instance == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ZonedDateTime.class,
                            new ZonedDateTimeDeserializer(DISQUS_DATE_TIME_FORMATTER))
                    .create();

            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(DisqusApiInterface.class);
        }

        return instance;
    }
}
