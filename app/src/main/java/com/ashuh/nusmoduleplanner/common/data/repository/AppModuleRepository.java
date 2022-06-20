package com.ashuh.nusmoduleplanner.common.data.repository;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.common.data.remote.source.ModuleRemoteDataSource;
import com.ashuh.nusmoduleplanner.common.data.local.source.ModuleLocalDataSource;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleInfo;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
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
        requireNonNull(remoteDataSource);
        requireNonNull(localDataSource);
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @NonNull
    public LiveData<List<ModuleInfo>> getAllModuleInfo(String acadYear) {
        return remoteDataSource.getAllModuleInfo(acadYear);
    }

    @NonNull
    public LiveData<Module> getModule(String acadYear, String moduleCode) {
        return remoteDataSource.getModule(acadYear, moduleCode);
    }

    @Override
    public void storeModule(Module module) {
        localDataSource.storeModule(module);
    }

    @Override
    public void storeModuleReading(ModuleReading moduleReading) {
        localDataSource.storeModuleReading(moduleReading);
    }

    @NonNull
    public LiveData<List<ModuleReading>> getModuleReadings(Semester semester) {
        return localDataSource.getModuleReadings(semester);
    }

    @Override
    public void deleteModuleReading(String moduleCode, Semester semester) {
        localDataSource.deleteModuleReading(moduleCode, semester);
    }

    @Override
    public void updateLessonNoMapping(String moduleCode, Semester semester, LessonType lessonType,
                                      String newLessonNo) {
        localDataSource.updateLessonAssignmentMapping(moduleCode, semester, lessonType,
                newLessonNo);
    }
}
