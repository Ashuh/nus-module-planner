package com.ashuh.nusmoduleplanner.data.model.nusmods.module;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.workload.Workload;

public abstract class BaseDetailedModule extends BaseModule {

    @NonNull
    protected final String acadYear;
    @NonNull
    protected final String description;
    @NonNull
    protected final String faculty;
    @NonNull
    protected final String department;
    @NonNull
    protected final Workload workload;
    protected final String preclusion;
    protected final String prerequisite;
    protected final String corequisite;
    protected final double moduleCredit;

    public BaseDetailedModule(@NonNull String moduleCode, @NonNull String title,
                              @NonNull String acadYear, @NonNull String description,
                              @NonNull String faculty, @NonNull String department,
                              double moduleCredit, @NonNull Workload workload, String preclusion,
                              String prerequisite, String corequisite) {
        super(moduleCode, title);
        this.acadYear = acadYear;
        this.description = description;
        this.faculty = faculty;
        this.department = department;
        this.moduleCredit = moduleCredit;
        this.workload = workload;
        this.preclusion = preclusion;
        this.prerequisite = prerequisite;
        this.corequisite = corequisite;
    }

    @NonNull
    public String getAcadYear() {
        return acadYear;
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
    public Workload getWorkload() {
        return workload;
    }

    public double getModuleCredit() {
        return moduleCredit;
    }

    public String getPreclusion() {
        return preclusion;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public String getCorequisite() {
        return corequisite;
    }

    public boolean hasPreclusion() {
        return preclusion != null && !preclusion.isEmpty();
    }

    public boolean hasPrerequisite() {
        return prerequisite != null && !prerequisite.isEmpty();
    }

    public boolean hasCorequisite() {
        return corequisite != null && !corequisite.isEmpty();
    }

    @NonNull
    @Override
    public String toString() {
        return "BaseDetailedModule{" +
                "acadYear='" + acadYear + '\'' +
                ", description='" + description + '\'' +
                ", faculty='" + faculty + '\'' +
                ", department='" + department + '\'' +
                ", moduleCredit=" + moduleCredit +
                ", preclusion='" + preclusion + '\'' +
                ", prerequisite='" + prerequisite + '\'' +
                ", corequisite='" + corequisite + '\'' +
                ", workload=" + workload +
                ", moduleCode='" + moduleCode + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
