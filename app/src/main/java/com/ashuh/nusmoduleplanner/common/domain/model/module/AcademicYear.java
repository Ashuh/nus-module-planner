package com.ashuh.nusmoduleplanner.common.domain.model.module;

import android.icu.util.Calendar;

import org.jetbrains.annotations.NotNull;

public class AcademicYear {

    private static final int ACADEMIC_YEAR_FIRST_MONTH = 8;

    private final int startYear;

    public AcademicYear(int startYear) {
        this.startYear = startYear;
    }

    public static AcademicYear getCurrent() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        if (month >= ACADEMIC_YEAR_FIRST_MONTH) {
            return new AcademicYear(year);
        } else {
            return new AcademicYear(year - 1);
        }
    }

    @NotNull
    @Override
    public String toString() {
        return startYear + "-" + (startYear + 1);
    }
}
