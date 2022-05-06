package com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

public class ModuleInformationSemesterDatum {

    @NonNull
    protected final SemesterType semester;
    protected final String examDate;
    protected final int examDuration;

    public ModuleInformationSemesterDatum(String examDate, int examDuration,
                                          @NonNull SemesterType semester) {
        requireNonNull(semester);
        this.examDate = examDate;
        this.examDuration = examDuration;
        this.semester = semester;
    }

    public String getExamDate() {
        return examDate;
    }

    @NonNull
    public SemesterType getSemester() {
        return semester;
    }

    public boolean hasExam() {
        return examDate != null && !examDate.isEmpty();
    }

    public int getExamDuration() {
        return examDuration;
    }

    @NonNull
    @Override
    public String toString() {
        return "ModuleInformationSemesterDatum{"
                + "examDate='" + examDate + '\''
                + ", examDuration=" + examDuration
                + ", semester=" + semester
                + '}';
    }
}
