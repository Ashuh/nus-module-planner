package com.ashuh.nusmoduleplanner.data.model.nusmods;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public abstract class Module {
    @NonNull
    @SerializedName("moduleCode")
    protected final String moduleCode;

    @SerializedName("title")
    protected final String title;

    @SerializedName("description")
    protected final String description;

    @SerializedName("moduleCredit")
    protected final Double credit;

    @SerializedName("department")
    protected final String department;

    @SerializedName("faculty")
    protected final String faculty;

    @SerializedName("prerequisite")
    protected final String prerequisite;

    @SerializedName("preclusion")
    protected final String preclusion;

    @SerializedName("corequisite")
    protected final String corequisite;

    protected Module(@NonNull String moduleCode, String title, String description, Double credit,
                     String department, String faculty, String prerequisite, String preclusion,
                     String corequisite) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.credit = credit;
        this.department = department;
        this.faculty = faculty;
        this.prerequisite = prerequisite;
        this.preclusion = preclusion;
        this.corequisite = corequisite;
    }

    @NonNull
    public String getModuleCode() {
        return moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDepartment() {
        return department;
    }

    public String getFaculty() {
        return faculty;
    }

    public double getCredit() {
        return credit;
    }

    public String getDescription() {
        return description;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public String getPreclusion() {
        return preclusion;
    }

    public String getCorequisite() {
        return corequisite;
    }

    public boolean hasPrerequisite() {
        return prerequisite != null && !prerequisite.isEmpty();
    }

    public boolean hasPreclusion() {
        return preclusion != null && !preclusion.isEmpty();
    }

    public boolean hasCorequisite() {
        return corequisite != null && !corequisite.isEmpty();
    }
}
