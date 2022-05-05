package com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.weeks;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.util.List;

public class Weeks {

    @NonNull
    protected List<Integer> weeks;

    public Weeks(@NonNull List<Integer> weeks) {
        requireNonNull(weeks);
        this.weeks = weeks;
    }

    @NonNull
    public List<Integer> getWeeks() {
        return weeks;
    }

    @NonNull
    @Override
    public String toString() {
        if (isWeeksContinuous()) {
            return "Weeks " + weeks.get(0) + "-" + weeks.get(weeks.size() - 1);
        } else {
            return "Weeks " + weeks.toString().replaceAll("[\\[\\]]", "");
        }    }

    protected boolean isWeeksContinuous() {
        if (weeks.size() <= 1) {
            return false;
        }

        for (int i = 1; i < weeks.size(); i++) {
            if (weeks.get(i) != weeks.get(i - 1) + 1) {
                return false;
            }
        }

        return true;
    }
}
