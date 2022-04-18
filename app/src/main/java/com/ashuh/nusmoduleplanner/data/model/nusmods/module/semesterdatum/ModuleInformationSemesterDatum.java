package com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.data.model.util.DateUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public class ModuleInformationSemesterDatum {

    @NonNull
    private final SemesterType semester;
    private final String examDate;
    private final int examDuration;

    public ModuleInformationSemesterDatum(String examDate, int examDuration,
                                          @NonNull SemesterType semester) {
        requireNonNull(semester);
        this.examDate = examDate;
        this.examDuration = examDuration;
        this.semester = semester;
    }

    public Optional<LocalDateTime> getExamDate() {
        if (!hasExam()) {
            return Optional.empty();
        }

        return Optional.of(LocalDateTime.parse(examDate, DateUtil.DATE_FORMATTER_API));
    }

    public boolean hasExam() {
        return examDate != null && !examDate.isEmpty();
    }

    @NonNull
    public SemesterType getSemester() {
        return semester;
    }

    public String getExamInfo() {
        if (hasExam()) {
            String examDateString = ZonedDateTime.parse(examDate)
                    .withZoneSameInstant(ZoneId.of("Asia/Singapore"))
                    .format(DateUtil.DATE_FORMATTER_DISPLAY);
            String examDurationString = examDuration / 60.0 + " hrs";
            return examDateString + " " + examDurationString;
        } else {
            return "No Exam";
        }
    }

    public Duration getExamDuration() {
        return Duration.ofMinutes(examDuration);
    }

    @NonNull
    @Override
    public String toString() {
        return "ModuleInformationSemesterDatum{" +
                "examDate='" + examDate + '\'' +
                ", examDuration=" + examDuration +
                ", semester=" + semester +
                '}';
    }
}
