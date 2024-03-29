package com.ashuh.nusmoduleplanner.common.data.local.model.module.reading;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleEntity;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleReadingAggregate {
    @NonNull
    @Embedded
    private final ModuleReadingEntity moduleReading;
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

    public ModuleReadingAggregate(@NonNull ModuleReadingEntity moduleReading,
                                  @NonNull List<LessonNoMappingEntity> lessonNoMappings,
                                  @NonNull ModuleAggregate moduleAggregate) {
        this.moduleReading = requireNonNull(moduleReading);
        this.lessonNoMappings = requireNonNull(lessonNoMappings);
        this.moduleAggregate = requireNonNull(moduleAggregate);
    }

    @NonNull
    public static ModuleReadingAggregate fromDomain(@NonNull ModuleReading moduleReading) {
        Semester semester = moduleReading.getSemester();
        int colorId = moduleReading.getColorId().getValue();
        String moduleCode = moduleReading.getModule().getModuleCode();
        ModuleReadingEntity moduleReadingEntity
                = new ModuleReadingEntity(moduleCode, semester, colorId);

        List<LessonNoMappingEntity> mappings = new ArrayList<>();
        moduleReading.getLessonNoMapping().forEach((lessonType, lessonNo) -> {
            LessonNoMappingEntity mapping = new LessonNoMappingEntity(lessonType, lessonNo);
            mappings.add(mapping);
        });

        ModuleAggregate moduleAggregate = ModuleAggregate.fromDomain(moduleReading.getModule());
        return new ModuleReadingAggregate(moduleReadingEntity, mappings, moduleAggregate);
    }

    @NonNull
    public ModuleReading toDomain() {
        Module module = moduleAggregate.toDomain();
        Semester semester = moduleReading.getSemester();
        ColorScheme.Index colorId = new ColorScheme.Index(moduleReading.getColorId());

        Map<LessonType, String> lessonTypeToLessonNo = new HashMap<>();
        lessonNoMappings.forEach(mapping -> {
            LessonType lessonType = mapping.getLessonType();
            String lessonNo = mapping.getLessonNo();
            lessonTypeToLessonNo.put(lessonType, lessonNo);
        });
        return new ModuleReading(module, semester, lessonTypeToLessonNo, colorId);
    }

    @NonNull
    public ModuleReadingEntity getModuleReading() {
        return moduleReading;
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
