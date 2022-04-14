package com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.weekrange;

import static com.ashuh.nusmoduleplanner.data.model.util.DateUtil.DATE_FORMATTER_DISPLAY_SHORT;
import static java.util.Objects.requireNonNull;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WeekRange {

    @NonNull
    private final List<Integer> weeks;
    private String start;
    private String end;
    private int weekInterval;

    public WeekRange(@NonNull String start, @NonNull String end, int weekInterval,
                     @NonNull List<Integer> weeks) {
        requireNonNull(start);
        requireNonNull(end);
        requireNonNull(weeks);
        this.start = start;
        this.end = end;
        this.weekInterval = weekInterval;
        this.weeks = weeks;
    }

    public WeekRange(@NonNull List<Integer> weeks) {
        requireNonNull(weeks);
        this.weeks = weeks;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String formatDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
                .format(DATE_FORMATTER_DISPLAY_SHORT);
    }

    @NonNull
    public List<Integer> getWeeks() {
        return weeks;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public String toString() {
        if (weeks.isEmpty()) {
            String startString = formatDate(start);
            String endString = formatDate(end);
            return startString + "-" + endString;
        }

        if (isWeeksContinuous()) {
            return "Weeks " + weeks.get(0) + "-" + weeks.get(weeks.size() - 1);
        } else {
            return "Weeks " + weeks.toString().replaceAll("[\\[\\]]", "");
        }
    }

    private boolean isWeeksContinuous() {
        if (weeks.size() <= 1) {
            return false;
        }

        for (int i = 1; i < weeks.size(); i++) {
            if (weeks.get(i) != weeks.get(i-1) + 1) {
                return false;
            }
        }

        return true;
    }
}
