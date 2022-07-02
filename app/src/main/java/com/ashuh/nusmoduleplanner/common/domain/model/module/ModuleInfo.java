package com.ashuh.nusmoduleplanner.common.domain.model.module;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ModuleInfo extends AbstractModule<ModuleInfoSemesterDatum> {
    public ModuleInfo(@NonNull String moduleCode, @NonNull String title,
                      @NonNull String description, @NonNull ModuleCredit moduleCredit,
                      @NonNull String department, @NonNull String faculty,
                      @Nullable Workload workload, @NonNull String prerequisite,
                      @NonNull String preclusion, @NonNull String coRequisite,
                      @NonNull List<ModuleInfoSemesterDatum> semesterData) {
        super(moduleCode, title, description, moduleCredit, department, faculty, workload,
                prerequisite, preclusion, coRequisite, semesterData);
    }
}
