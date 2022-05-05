package com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.weeks;

import static com.ashuh.nusmoduleplanner.data.model.util.DateUtil.DATE_FORMATTER_DISPLAY_SHORT;
import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WeekRange extends Weeks {

    @NonNull
    private final String start;
    @NonNull
    private final String end;
    private final int weekInterval;

    public WeekRange(@NonNull String start, @NonNull String end, int weekInterval,
                     List<Integer> weeks) {
        super(weeks);
        requireNonNull(start);
        requireNonNull(end);
        this.start = start;
        this.end = end;
        this.weekInterval = weekInterval;
    }

    @NonNull
    public String getStart() {
        return start;
    }

    @NonNull
    public String getEnd() {
        return end;
    }

    public int getWeekInterval() {
        return weekInterval;
    }

    @NonNull
    @Override
    public String toString() {
        if (weeks.isEmpty()) {
            String startString = formatDate(start);
            String endString = formatDate(end);
            return startString + "-" + endString;
        }

        return super.toString();
    }

    private static String formatDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
                .format(DATE_FORMATTER_DISPLAY_SHORT);
    }
}
