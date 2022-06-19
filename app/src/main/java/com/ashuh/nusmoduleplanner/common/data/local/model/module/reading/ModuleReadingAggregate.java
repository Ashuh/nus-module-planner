package com.ashuh.nusmoduleplanner.common.data.local.model.module.reading;

import static java.util.Objects.requireNonNull;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleEntity;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleReadingAggregate {
    @NonNull
    @Embedded
    private final ModuleReadingEntity timetableEntry;
    @NonNull
    @Relation(
            parentColumn = "id",
            entityColumn = "ownerId",
            entity = LessonNoMappingEntity.class
    )
    private final List<LessonNoMappingEntity> lessonNoMappings;
    @NonNull
    @Relation(
            parentColumn = "moduleCode",
            entityColumn = "moduleCode",
            entity = ModuleEntity.class
    )
    private final ModuleAggregate moduleAggregate;

    public ModuleReadingAggregate(@NonNull ModuleReadingEntity timetableEntry,
                                  @NonNull List<LessonNoMappingEntity> lessonNoMappings,
                                  @NonNull ModuleAggregate moduleAggregate) {
        requireNonNull(timetableEntry);
        requireNonNull(lessonNoMappings);
        requireNonNull(moduleAggregate);
        this.timetableEntry = timetableEntry;
        this.lessonNoMappings = lessonNoMappings;
        this.moduleAggregate = moduleAggregate;
    }

    @NonNull
    public static ModuleReadingAggregate fromDomain(@NonNull ModuleReading moduleReading) {
        Semester semester = moduleReading.getSemester();
        int color = moduleReading.getColor().toArgb();
        String moduleCode = moduleReading.getModule().getModuleCode();
        ModuleReadingEntity moduleReadingEntity
                = new ModuleReadingEntity(moduleCode, semester, color);

        List<LessonNoMappingEntity> mappings = new ArrayList<>();
        moduleReading.getLessonAssignmentMapping().forEach((lessonType, lessonNo) -> {
            LessonNoMappingEntity mapping
                    = new LessonNoMappingEntity(lessonType.name(), lessonNo);
            mappings.add(mapping);
        });

        ModuleAggregate moduleAggregate = ModuleAggregate.fromDomain(moduleReading.getModule());
        return new ModuleReadingAggregate(moduleReadingEntity, mappings, moduleAggregate);
    }

    @NonNull
    public ModuleReading toDomain() {
        Module module = moduleAggregate.toDomain();
        Semester semester = timetableEntry.getSemester();
        Color color = Color.valueOf(timetableEntry.getColor());

        Map<LessonType, String> lessonTypeToLessonNo = new HashMap<>();
        lessonNoMappings.forEach(mapping -> {
            LessonType lessonType = LessonType.valueOf(mapping.getLessonType());
            String lessonNo = mapping.getLessonNo();
            lessonTypeToLessonNo.put(lessonType, lessonNo);
        });
        return new ModuleReading(module, semester, lessonTypeToLessonNo, color);
    }

    @NonNull
    public ModuleReadingEntity getTimetableEntry() {
        return timetableEntry;
    }

    @NonNull
    public List<LessonNoMappingEntity> getLessonNoMappings() {
        return lessonNoMappings;
    }

    @NonNull
    public ModuleAggregate getModuleAggregate() {
        return moduleAggregate;
    }
}
