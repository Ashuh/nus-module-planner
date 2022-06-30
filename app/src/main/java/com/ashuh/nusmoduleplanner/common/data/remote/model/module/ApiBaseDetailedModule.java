package com.ashuh.nusmoduleplanner.common.data.remote.model.module;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;

public abstract class ApiBaseDetailedModule extends ApiBaseModule {
    @NonNull
    @SerializedName("description")
    protected final String description;
    @NonNull
    @SerializedName("faculty")
    protected final String faculty;
    @NonNull
    @SerializedName("department")
    protected final String department;
    @Nullable
    @SerializedName("workload")
    protected final ApiWorkloadResponse workload;
    @Nullable
    @SerializedName("preclusion")
    protected final String preclusion;
    @Nullable
    @SerializedName("prerequisite")
    protected final String prerequisite;
    @Nullable
    @SerializedName("corequisite")
    protected final String coRequisite;
    @SerializedName("moduleCredit")
    protected final double moduleCredit;

    public ApiBaseDetailedModule(String moduleCode, String title, @NonNull String description,
                                 @NonNull String faculty, @NonNull String department,
                                 @Nullable ApiWorkloadResponse workload,
                                 @Nullable String preclusion, @Nullable String prerequisite,
                                 @Nullable String coRequisite, double moduleCredit) {
        super(moduleCode, title);
        requireNonNull(description);
        requireNonNull(faculty);
        requireNonNull(department);
        this.description = description;
        this.faculty = faculty;
        this.department = department;
        this.workload = workload;
        this.preclusion = preclusion;
        this.prerequisite = prerequisite;
        this.coRequisite = coRequisite;
        this.moduleCredit = moduleCredit;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getFaculty() {
        return faculty;
    }

    @NonNull
    public String getDepartment() {
        return department;
    }

    @NonNull
    public Optional<ApiWorkloadResponse> getWorkload() {
        return Optional.ofNullable(workload);
    }

    @NonNull
    public String getPreclusion() {
        return requireNonNullElse(preclusion, "");
    }

    @NonNull
    public String getPrerequisite() {
        return requireNonNullElse(prerequisite, "");
    }

    @NonNull
    public String getCoRequisite() {
        return requireNonNullElse(coRequisite, "");
    }

    public double getModuleCredit() {
        return moduleCredit;
    }
}
