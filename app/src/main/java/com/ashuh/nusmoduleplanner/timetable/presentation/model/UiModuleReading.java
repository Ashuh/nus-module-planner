package com.ashuh.nusmoduleplanner.timetable.presentation.model;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Exam;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.util.DateUtil;

import java.time.ZonedDateTime;

public class UiModuleReading {
    @NonNull
    private final String moduleCode;
    @NonNull
    private final String title;
    @NonNull
    private final String moduleCredit;
    @NonNull
    private final String examDate;
    private final int color;

    public UiModuleReading(@NonNull String moduleCode, @NonNull String title,
                           @NonNull String moduleCredit, @NonNull String examDate, int color) {
        requireNonNull(moduleCode);
        requireNonNull(title);
        requireNonNull(moduleCredit);
        requireNonNull(examDate);
        this.moduleCode = moduleCode;
        this.title = title;
        this.moduleCredit = moduleCredit;
        this.examDate = examDate;
        this.color = color;
    }

    public static UiModuleReading fromDomain(ModuleReading moduleReading) {
        String moduleCode = moduleReading.getModule().getModuleCode();
        String title = moduleReading.getModule().getTitle();
        String moduleCredit = moduleReading.getModule().getModuleCredit().toString();
        String examDate = moduleReading.getExam()
                .map(Exam::getDate)
                .map(UiModuleReading::formatDate)
                .orElse("");
        int color = moduleReading.getColor().toArgb();
        return new UiModuleReading(moduleCode, title, moduleCredit, examDate, color);
    }

    private static String formatDate(ZonedDateTime dateTime) {
        return DateUtil.formatZonedDateTimeForDisplay(dateTime);
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
    public String getExamDate() {
        return examDate;
    }

    public int getColor() {
        return color;
    }
}
