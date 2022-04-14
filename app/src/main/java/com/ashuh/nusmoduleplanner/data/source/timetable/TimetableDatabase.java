package com.ashuh.nusmoduleplanner.data.source.timetable;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.LessonType;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.ModuleSemesterDatum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
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

        @RequiresApi(api = Build.VERSION_CODES.O)
        @TypeConverter
        public static ModuleSemesterDatum toModuleSemesterDatum(String string) {
            if (string == null) {
                return null;
            }

            return gson.fromJson(string, ModuleSemesterDatum.class);
        }

        @TypeConverter
        public static String fromModuleSemesterDatum(ModuleSemesterDatum semesterData) {
            if (semesterData == null) {
                return null;
            }

            return gson.toJson(semesterData);
        }

        @TypeConverter
        public static Map<LessonType, String> toLessonTypeMap(String string) {
            if (string == null) {
                return null;
            }

            Map<LessonType, String> map = new HashMap<>();
            Type mapType = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> temp = gson.fromJson(string, mapType);

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

            return gson.toJson(map);
        }
    }
}
