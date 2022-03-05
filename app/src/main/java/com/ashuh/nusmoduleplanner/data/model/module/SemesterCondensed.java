package com.ashuh.nusmoduleplanner.data.model.module;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

public class SemesterCondensed {
    @SerializedName("semester")
    protected final Semester semester;

    @SerializedName("examDate")
    protected final String examDate;

    @SerializedName("examDuration")
    protected final int examDuration;

    public SemesterCondensed(Semester semester, String examDate, int examDuration) {
        this.semester = semester;
        this.examDate = examDate;
        this.examDuration = examDuration;
    }

    public Semester getSemester() {
        return semester;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Optional<String> getExamDate() {
        if (examDate == null || examDate.isEmpty()) {
            return Optional.empty();
        } else {
            DateTimeFormatter inputFormatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
            DateTimeFormatter outputFormatter = DateTimeFormatter
                    .ofPattern("dd-MMM-yyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(examDate, inputFormatter);
            String formattedDate = outputFormatter.format(date);
            return Optional.ofNullable(formattedDate);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Duration getExamDuration() {
        return Duration.ofMinutes(examDuration);
    }

    public boolean hasExam() {
        return examDate != null && !examDate.isEmpty();
    }
}
