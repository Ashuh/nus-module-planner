package com.ashuh.nusmoduleplanner.common.data.local.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ashuh.nusmoduleplanner.common.data.local.dao.ModuleDao;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.ExamEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleRequirementCrossRefEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.WorkloadEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.lesson.LessonEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.lessonoccurrence.LessonOccurrenceEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.prerequisite.PrerequisiteTreeEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.prerequisite.PrerequisiteTreeNodeEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.reading.LessonNoMappingEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.reading.ModuleReadingEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.semesterdatum.SemesterDatumEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks.WeekNumberEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks.WeeksEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks.WeeksWeekNumberCrossRefEntity;

@Database(
        entities = {
                ModuleEntity.class,
                ModuleRequirementCrossRefEntity.class,
                WorkloadEntity.class,
                PrerequisiteTreeEntity.class,
                PrerequisiteTreeNodeEntity.class,
                SemesterDatumEntity.class,
                ExamEntity.class,
                LessonEntity.class,
                LessonOccurrenceEntity.class,
                WeeksEntity.class,
                WeekNumberEntity.class,
                WeeksWeekNumberCrossRefEntity.class,
                ModuleReadingEntity.class,
                LessonNoMappingEntity.class
        },
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "modules_db";
    private static AppDatabase instance;

    @NonNull
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract ModuleDao dao();
}
