package com.ashuh.nusmoduleplanner.common.domain.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleInfo;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import java.util.List;

public interface ModuleRepository {
    @NonNull
    LiveData<List<ModuleInfo>> getAllModuleInfo(String acadYear);

    @NonNull
    LiveData<Module> getModule(String acadYear, String moduleCode);

    @NonNull
    LiveData<List<ModuleReading>> getModuleReadings(Semester semester);

    void storeModule(Module module);

    void storeModuleReading(ModuleReading moduleReading);

    void deleteModuleReading(String moduleCode, Semester semester);

    void updateLessonNoMapping(String moduleCode, Semester semType, LessonType lessonType,
                               String newLessonNo);
}
