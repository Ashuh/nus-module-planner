package com.ashuh.nusmoduleplanner.modulelist.presentation.model;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

public class UiModuleInfo {
    @NonNull
    private final String moduleCode;
    @NonNull
    private final String title;
    @NonNull
    private final String department;
    @NonNull
    private final String moduleCredit;

    public UiModuleInfo(@NonNull String moduleCode, @NonNull String title,
                        @NonNull String department, @NonNull String moduleCredit) {
        this.moduleCode = requireNonNull(moduleCode);
        this.title =  requireNonNull(title);
        this.department = requireNonNull(department);
        this.moduleCredit = requireNonNull(moduleCredit);
    }

    @NonNull
    public String getModuleCode() {
        return moduleCode;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getDepartment() {
        return department;
    }

    @NonNull
    public String getModuleCredit() {
        return moduleCredit;
    }
}
