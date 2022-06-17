package com.ashuh.nusmoduleplanner.common.data.remote.model.module.semesterdatum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Exam;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleInfoSemesterDatum;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ApiModuleInfoSemesterDatum {
    private static final int SEMESTER_1_API = 1;
    private static final int SEMESTER_2_API = 2;
    private static final int SPECIAL_TERM_1_API = 3;
    private static final int SPECIAL_TERM_2_API = 4;

    protected final int semester;
    @Nullable
    protected final String examDate;
    @Nullable
    protected final Integer examDuration;

    public ApiModuleInfoSemesterDatum(int semester, @Nullable String examDate,
                                      @Nullable Integer examDuration) {
        this.semester = semester;
        this.examDate = examDate;
        this.examDuration = examDuration;
    }

    public int getSemester() {
        return semester;
    }

    @NonNull
    public Optional<String> getExamDate() {
        return Optional.ofNullable(examDate);
    }

    @NonNull
    public Optional<Integer> getExamDuration() {
        return Optional.ofNullable(examDuration);
    }

    @NonNull
    public ModuleInfoSemesterDatum toDomain() {
        Semester domainSemester = mapSemester(semester);
        Exam domainExam = mapExam(examDate, examDuration);
        return new ModuleInfoSemesterDatum(domainSemester, domainExam);
    }

    @NonNull
    protected static Semester mapSemester(Integer apiSemester) {
        switch (apiSemester) {
            case SEMESTER_1_API:
                return Semester.SEMESTER_1;
            case SEMESTER_2_API:
                return Semester.SEMESTER_2;
            case SPECIAL_TERM_1_API:
                return Semester.SPECIAL_TERM_1;
            case SPECIAL_TERM_2_API:
                return Semester.SPECIAL_TERM_2;
            default:
                throw new IllegalArgumentException("Invalid semester: " + apiSemester);
        }
    }

    @Nullable
    protected static Exam mapExam(String examDate, Integer examDuration) {
        if (examDate == null || examDuration == null) {
            return null;
        }

        ZonedDateTime domainDateTime
                = ZonedDateTime.parse(examDate, DateTimeFormatter.ISO_DATE_TIME);
        Duration domainDuration = Duration.ofMinutes(examDuration);
        return new Exam(domainDateTime, domainDuration);
    }
}
