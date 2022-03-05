package com.ashuh.nusmoduleplanner.data.model.nusmods;

import androidx.annotation.NonNull;

import java.util.List;

public class Weeks {

    private final List<Integer> weeks;

    public Weeks(List<Integer> weeks) {
        this.weeks=weeks;
    }

    public List<Integer> getWeeks() {
        return weeks;
    }

    @NonNull
    @Override
    public String toString() {
        return "Weeks{" +
                "weeks=" + weeks +
                '}';
    }
}
