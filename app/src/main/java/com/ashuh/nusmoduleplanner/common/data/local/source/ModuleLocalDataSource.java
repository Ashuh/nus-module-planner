package com.ashuh.nusmoduleplanner.common.data.local.source;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.ashuh.nusmoduleplanner.common.data.local.dao.ModuleDao;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.reading.ModuleReadingAggregate;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ModuleLocalDataSource {
    private final Executor executor = Executors.newSingleThreadExecutor();
    @NonNull
    private final ModuleDao moduleDao;

    public ModuleLocalDataSource(@NonNull ModuleDao moduleDao) {
        this.moduleDao = requireNonNull(moduleDao);
    }

    public void storeModule(@NonNull Module module) {
        executor.execute(() -> {
            ModuleAggregate moduleAggregate = ModuleAggregate.fromDomain(module);
            moduleDao.insertModule(moduleAggregate);
        });
    }

    public LiveData<List<ModuleReading>> getModuleReadings(@NonNull Semester semester) {
        return Transformations.map(moduleDao.getModuleReadings(semester),
                readingAggregates -> readingAggregates.stream()
                        .map(ModuleReadingAggregate::toDomain)
                        .collect(Collectors.toList()));
    }

    public void deleteModuleReading(@NonNull String moduleCode, @NonNull Semester semester) {
        executor.execute(() -> moduleDao.deleteModuleReading(moduleCode, semester));
    }

    public void storeModuleReading(@NonNull ModuleReading moduleReading) {
        executor.execute(() -> {
            ModuleReadingAggregate moduleReadingAggregate
                    = ModuleReadingAggregate.fromDomain(moduleReading);
            moduleDao.insertModuleReading(moduleReadingAggregate);
        });
    }

    public void updateLessonAssignmentMapping(@NonNull String moduleCode,
                                              @NonNull Semester semester,
                                              @NonNull LessonType lessonType,
                                              @NonNull String newLessonNo) {
        executor.execute(() -> moduleDao.updateLessonNoMapping(moduleCode, semester,
                lessonType, newLessonNo));
    }

    public void updateColor(String moduleCode, Semester semester, int newColorId) {
        executor.execute(() -> moduleDao.updateColor(moduleCode, semester, newColorId));
    }
}
