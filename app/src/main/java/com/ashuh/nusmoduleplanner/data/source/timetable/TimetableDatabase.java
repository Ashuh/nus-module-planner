package com.ashuh.nusmoduleplanner.data.source.timetable;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.Lesson;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.LessonType;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.weeks.Weeks;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.model.typeadapter.deserializer.WeeksDeserializer;
import com.ashuh.nusmoduleplanner.data.model.typeadapter.serializer.WeeksSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Database(entities = {AssignedModule.class}, version = 1)
@TypeConverters({TimetableDatabase.Converter.class})
public abstract class TimetableDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "timetable_db";

    private static TimetableDatabase instance;

    public static TimetableDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TimetableDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract TimetableDAO dao();

    public static class Converter {
        private static final Gson GSON = new GsonBuilder()
                .registerTypeAdapter(Weeks.class, new WeeksDeserializer())
                .registerTypeAdapter(Weeks.class, new WeeksSerializer())
                .create();

        @TypeConverter
        public static ZonedDateTime toZonedDateTime(String string) {
            if (string == null) {
                return null;
            }

            return ZonedDateTime.parse(string);
        }

        @TypeConverter
        public static String fromZonedDateTime(ZonedDateTime zonedDateTime) {
            if (zonedDateTime == null) {
                return null;
            }

            return zonedDateTime.toString();
        }

        @TypeConverter
        public static List<Lesson> toLessonList(String string) {
            if (string == null) {
                return null;
            }

            Type listType = new TypeToken<List<Lesson>>() {
            }.getType();
            return GSON.fromJson(string, listType);
        }

        @TypeConverter
        public static String fromLessonList(List<Lesson> lessons) {
            if (lessons == null) {
                return null;
            }

            return GSON.toJson(lessons);
        }

        @TypeConverter
        public static Map<LessonType, String> toLessonTypeMap(String string) {
            if (string == null) {
                return null;
            }

            Map<LessonType, String> map = new HashMap<>();
            Type mapType = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> temp = GSON.fromJson(string, mapType);

            for (String key : temp.keySet()) {
                map.put(LessonType.fromString(key), temp.get(key));
            }

            return map;
        }

        @TypeConverter
        public static String fromLessonTypeMap(Map<LessonType, String> map) {
            if (map == null) {
                return null;
            }

            return GSON.toJson(map);
        }
    }
}
