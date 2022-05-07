package com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.time.ZonedDateTime;

public class ModuleInformationSemesterDatum {

    @NonNull
    protected final SemesterType semester;
    protected final ZonedDateTime examDate;
    protected final int examDuration;

    public ModuleInformationSemesterDatum(ZonedDateTime examDate, int examDuration,
                                          @NonNull SemesterType semester) {
        requireNonNull(semester);
        this.examDate = examDate;
        this.examDuration = examDuration;
        this.semester = semester;
    }

    public ZonedDateTime getExamDate() {
        return examDate;
    }

    @NonNull
    public SemesterType getSemester() {
        return semester;
    }

    public boolean hasExam() {
        return examDate != null;
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
