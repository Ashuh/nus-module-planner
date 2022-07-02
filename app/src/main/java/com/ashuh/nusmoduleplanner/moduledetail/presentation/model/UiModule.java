package com.ashuh.nusmoduleplanner.moduledetail.presentation.model;

import static java.util.Objects.requireNonNull;

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
    private final List<UiSemester> semestersOffered;

    public UiModule(@NonNull String moduleCode, @NonNull String title, @NonNull String moduleCredit,
                    @NonNull String department, @NonNull String faculty,
                    @NonNull String description, @NonNull String prerequisite,
                    @NonNull String coRequisite, @NonNull String preclusion,
                    @NonNull Map<String, UiExam> semesterToExam,
                    @NonNull List<UiSemester> semestersOffered) {
        this.moduleCode = requireNonNull(moduleCode);
        this.title = requireNonNull(title);
        this.moduleCredit = requireNonNull(moduleCredit);
        this.department = requireNonNull(department);
        this.faculty = requireNonNull(faculty);
        this.description = requireNonNull(description);
        this.prerequisite = requireNonNull(prerequisite);
        this.coRequisite = requireNonNull(coRequisite);
        this.preclusion = requireNonNull(preclusion);
        this.semesterToExam = requireNonNull(semesterToExam);
        this.semestersOffered = requireNonNull(semestersOffered);
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
    public List<UiSemester> getSemestersOffered() {
        return semestersOffered;
    }
}
