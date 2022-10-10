package com.ashuh.nusmoduleplanner.common.domain.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleInfo;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme.Index;

import java.util.List;

public interface ModuleRepository {
    @NonNull
    LiveData<List<ModuleInfo>> getAllModuleInfo(@NonNull String acadYear);

    @NonNull
    LiveData<Module> getModule(@NonNull String acadYear, @NonNull String moduleCode);

    @NonNull
    LiveData<List<ModuleReading>> getModuleReadings(@NonNull Semester semester);

    void storeModule(@NonNull Module module);

    void storeModuleReading(@NonNull ModuleReading moduleReading);

    void deleteModuleReading(@NonNull String moduleCode, @NonNull Semester semester);

    void updateLessonNoMapping(@NonNull String moduleCode, @NonNull Semester semType,
                               @NonNull LessonType lessonType, @NonNull String newLessonNo);

    void updateColor(@NonNull String moduleCode, @NonNull Semester semester,
                     @NonNull Index newColorId);
}
