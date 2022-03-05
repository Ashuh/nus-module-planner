package com.ashuh.nusmoduleplanner.data.model.module;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModuleCondensed extends Module {
    @SerializedName("semesterData")
    protected final List<SemesterCondensed> condensedSemesters;

    public ModuleCondensed(@NonNull String moduleCode, String title, String description,
                           double credit,
                           String department, String faculty, String prerequisite,
                           String preclusion, String corequisite,
                           List<SemesterCondensed> condensedSemesters) {
        super(moduleCode, title, description, credit, department, faculty, prerequisite,
                preclusion, corequisite);
        this.condensedSemesters = condensedSemesters;
    }

    public List<SemesterCondensed> getCondensedSemesters() {
        return condensedSemesters;
    }
}
