package com.ashuh.nusmoduleplanner.common.data.repository;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.common.data.local.source.ModuleLocalDataSource;
import com.ashuh.nusmoduleplanner.common.data.remote.source.ModuleRemoteDataSource;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleInfo;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;

import java.util.List;

public class AppModuleRepository implements ModuleRepository {
    @NonNull
    private final ModuleRemoteDataSource remoteDataSource;
    @NonNull
    private final ModuleLocalDataSource localDataSource;

    public AppModuleRepository(@NonNull ModuleRemoteDataSource remoteDataSource,
                               @NonNull ModuleLocalDataSource localDataSource) {
        this.remoteDataSource = requireNonNull(remoteDataSource);
        this.localDataSource = requireNonNull(localDataSource);
    }

    @NonNull
    public LiveData<List<ModuleInfo>> getAllModuleInfo(@NonNull String acadYear) {
        requireNonNull(acadYear);
        return remoteDataSource.getAllModuleInfo(acadYear);
    }

    @NonNull
    public LiveData<Module> getModule(@NonNull String acadYear, @NonNull String moduleCode) {
        requireNonNull(acadYear);
        requireNonNull(moduleCode);
        return remoteDataSource.getModule(acadYear, moduleCode);
    }

    @NonNull
    public LiveData<List<ModuleReading>> getModuleReadings(@NonNull Semester semester) {
        requireNonNull(semester);
        return localDataSource.getModuleReadings(semester);
    }

    @Override
    public void storeModule(@NonNull Module module) {
        requireNonNull(module);
        localDataSource.storeModule(module);
    }

    @Override
    public void storeModuleReading(@NonNull ModuleReading moduleReading) {
        requireNonNull(moduleReading);
        localDataSource.storeModuleReading(moduleReading);
    }

    @Override
    public void deleteModuleReading(@NonNull String moduleCode, @NonNull Semester semester) {
        requireNonNull(moduleCode);
        requireNonNull(semester);
        localDataSource.deleteModuleReading(moduleCode, semester);
    }

    @Override
    public void updateLessonNoMapping(@NonNull String moduleCode, @NonNull Semester semester,
                                      @NonNull LessonType lessonType, @NonNull String newLessonNo) {
        requireNonNull(moduleCode);
        requireNonNull(semester);
        requireNonNull(lessonType);
        requireNonNull(newLessonNo);
        localDataSource.updateLessonAssignmentMapping(moduleCode, semester, lessonType,
                newLessonNo);
    }
}
