package com.ashuh.nusmoduleplanner.moduledetail.presentation.model;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Exam;
import com.ashuh.nusmoduleplanner.common.util.DateUtil;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.Locale;

public class UiExam {
    private static final int MINUTES_PER_HOUR = 60;
    private static final String DURATION_FORMAT = "%s hrs";
    private static final String HOURS_FORMAT = "0.#";
    private static final DecimalFormat HOURS_FORMATTER = new DecimalFormat(HOURS_FORMAT);

    @NonNull
    private final String date;
    @NonNull
    private final String duration;

    public UiExam(@NonNull String date, @NonNull String duration) {
        requireNonNull(date);
        requireNonNull(duration);
        this.date = date;
        this.duration = duration;
    }

    public static UiExam fromDomain(@NonNull Exam exam) {
        String date = exam.getDate()
                .withZoneSameInstant(ZoneId.systemDefault())
                .format(DateUtil.DATE_FORMATTER_DISPLAY);
        double hours = (double) exam.getDuration().toMinutes() / MINUTES_PER_HOUR;
        String hoursFormatted = HOURS_FORMATTER.format(hours);
        String duration = String.format(Locale.ENGLISH, DURATION_FORMAT, hoursFormatted);
        return new UiExam(date, duration);
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public String getDuration() {
        return duration;
    }
}
