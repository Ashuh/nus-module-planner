package com.ashuh.nusmoduleplanner.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.Month;

public class AcademicYear {
    private final int startYear;

    public AcademicYear(int startYear) {
        this.startYear = startYear;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static AcademicYear getCurrent() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        Month month = now.getMonth();

        if (month.getValue() >= Month.MAY.getValue()) {
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
