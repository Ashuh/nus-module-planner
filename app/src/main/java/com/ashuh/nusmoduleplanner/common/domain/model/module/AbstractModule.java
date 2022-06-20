package com.ashuh.nusmoduleplanner.common.domain.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractModule<T extends ModuleInfoSemesterDatum> {
    @NonNull
    protected final String moduleCode;
    @NonNull
    protected final String title;
    @NonNull
    protected final String description;
    @NonNull
    protected final ModuleCredit moduleCredit;
    @NonNull
    protected final String department;
    @NonNull
    protected final String faculty;
    @Nullable
    protected final Workload workload;
    @NonNull
    protected final String prerequisite;
    @NonNull
    protected final String preclusion;
    @NonNull
    protected final String coRequisite;
    @NonNull
    protected final List<T> semesterData;

    public AbstractModule(@NonNull String moduleCode, @NonNull String title,
                          @NonNull String description, @NonNull ModuleCredit moduleCredit,
                          @NonNull String department, @NonNull String faculty,
                          @Nullable Workload workload, @NonNull String prerequisite,
                          @NonNull String preclusion, @NonNull String coRequisite,
                          @NonNull List<T> semesterData) {
        requireNonNull(moduleCode);
        requireNonNull(title);
        requireNonNull(description);
        requireNonNull(moduleCredit);
        requireNonNull(department);
        requireNonNull(faculty);
        requireNonNull(prerequisite);
        requireNonNull(preclusion);
        requireNonNull(coRequisite);
        requireNonNull(semesterData);
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.moduleCredit = moduleCredit;
        this.department = department;
        this.faculty = faculty;
        this.workload = workload;
        this.prerequisite = prerequisite;
        this.preclusion = preclusion;
        this.coRequisite = coRequisite;
        this.semesterData = semesterData;
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
    public String getDescription() {
        return description;
    }

    @NonNull
    public ModuleCredit getModuleCredit() {
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
    public Optional<Workload> getWorkload() {
        return Optional.ofNullable(workload);
    }

    @NonNull
    public String getPrerequisite() {
        return prerequisite;
    }

    @NonNull
    public String getPreclusion() {
        return preclusion;
    }

    @NonNull
    public String getCoRequisite() {
        return coRequisite;
    }

    public boolean isOffered() {
        return semesterData.size() > 0;
    }

    public boolean isOffered(Semester semester) {
        return getSemesterDatum(semester).isPresent();
    }

    @NonNull
    public Optional<T> getSemesterDatum(Semester semester) {
        return semesterData.stream()
                .filter(datum -> datum.getSemester() == semester)
                .findFirst();
    }

    @NonNull
    public List<T> getSemesterData() {
        return semesterData;
    }

    @NonNull
    public Optional<Exam> getExam(Semester semester) {
        return getSemesterDatum(semester).flatMap(T::getExam);
    }

    @NonNull
    public Map<Semester, Exam> getExams() {
        Map<Semester, Exam> semesterToExam = new HashMap<>();
        semesterData.forEach(datum -> datum.getExam()
                .ifPresent(exam -> semesterToExam.put(datum.getSemester(), exam)));
        return semesterToExam;
    }

    @NonNull
    public Set<Semester> getSemesters() {
        return semesterData.stream()
                .map(T::getSemester)
                .collect(Collectors.toSet());
    }
}
