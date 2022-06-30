package com.ashuh.nusmoduleplanner.moduledetail.presentation.model;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

public class UiModule {
    @NonNull
    private final String moduleCode;
    @NonNull
    private final String title;
    @NonNull
    private final String moduleCredit;
    @NonNull
    private final String department;
    @NonNull
    private final String faculty;
    @NonNull
    private final String description;
    @NonNull
    private final String prerequisite;
    @NonNull
    private final String coRequisite;
    @NonNull
    private final String preclusion;
    @NonNull
    private final Map<String, UiExam> semesterToExam;
    @NonNull
    private final List<String> semestersOffered;

    public UiModule(@NonNull String moduleCode, @NonNull String title,
                    @NonNull String moduleCredit, @NonNull String department,
                    @NonNull String faculty, @NonNull String description,
                    @NonNull String prerequisite, @NonNull String coRequisite,
                    @NonNull String preclusion, @NonNull Map<String, UiExam> semesterToExam,
                    @NonNull List<String> semestersOffered) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.moduleCredit = moduleCredit;
        this.department = department;
        this.faculty = faculty;
        this.description = description;
        this.prerequisite = prerequisite;
        this.coRequisite = coRequisite;
        this.preclusion = preclusion;
        this.semesterToExam = semesterToExam;
        this.semestersOffered = semestersOffered;
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
    public String getModuleCredit() {
        return moduleCredit;
    }

    @NonNull
    public String getDepartment() {
        return department;
    }

    @NonNull
    public String getFaculty() {
        return faculty;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getPrerequisite() {
        return prerequisite;
    }

    @NonNull
    public String getCoRequisite() {
        return coRequisite;
    }

    @NonNull
    public String getPreclusion() {
        return preclusion;
    }

    @NonNull
    public Map<String, UiExam> getExams() {
        return semesterToExam;
    }

    @NonNull
    public List<String> getSemestersOffered() {
        return semestersOffered;
    }
}
