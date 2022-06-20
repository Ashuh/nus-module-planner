package com.ashuh.nusmoduleplanner.common.domain.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Optional;

public class ModuleInfoSemesterDatum {
    @NonNull
    protected final Semester semester;
    @Nullable
    protected final Exam exam;

    public ModuleInfoSemesterDatum(@NonNull Semester semester, @Nullable Exam exam) {
        requireNonNull(semester);
        this.semester = semester;
        this.exam = exam;
    }

    @NonNull
    public Semester getSemester() {
        return semester;
    }

    @NonNull
    public Optional<Exam> getExam() {
        return Optional.ofNullable(exam);
    }
}
