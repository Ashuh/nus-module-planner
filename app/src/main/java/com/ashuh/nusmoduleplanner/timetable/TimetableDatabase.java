package com.ashuh.nusmoduleplanner.timetable;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.ashuh.nusmoduleplanner.data.module.Lesson;
import com.ashuh.nusmoduleplanner.data.module.SemesterDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Database(entities = {AssignedModule.class}, version = 1)
@TypeConverters({TimetableDatabase.Converter.class})
public abstract class TimetableDatabase extends RoomDatabase {
    private static TimetableDatabase instance;

    public static void init(Context context) {
        instance = Room.databaseBuilder(context,
                TimetableDatabase.class, "assigned_modules")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public static TimetableDatabase getDatabase() {
        if (instance == null) {
            throw new RuntimeException("Database has not been initialized");
        }
        return instance;
    }

    public abstract TimetableDAO dao();

    public static class Converter {
        private static final Gson gson = new Gson();

        @TypeConverter
        public static List<SemesterDetail> toSemesterDataList(String string) {
            if (string == null) {
                return null;
            }

//            Log.d("json", string);
            Type listType = new TypeToken<List<SemesterDetail>>() {
            }.getType();

            return gson.fromJson(string, listType);
        }

        @TypeConverter
        public static String fromSemesterDataList(List<SemesterDetail> semesterDetails) {
            if (semesterDetails == null) {
                return null;
            }

            return gson.toJson(semesterDetails);
        }

        @TypeConverter
        public static Map<Lesson.Type, String> toLessonTypeMap(String string) {
            if (string == null) {
                return null;
            }

            Map<Lesson.Type, String> map = new HashMap<>();
            Type mapType = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> temp = gson.fromJson(string, mapType);

            for (String key : temp.keySet()) {
                map.put(Lesson.Type.fromString(key), temp.get(key));
            }

            return map;
        }

        @TypeConverter
        public static String fromLessonTypeMap(Map<Lesson.Type, String> map) {
            if (map == null) {
                return null;
            }

            return gson.toJson(map);
        }
    }
}
