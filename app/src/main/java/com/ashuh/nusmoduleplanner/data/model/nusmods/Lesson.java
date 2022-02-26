package com.ashuh.nusmoduleplanner.data.model.nusmods;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.threeten.bp.DayOfWeek;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import me.jlurena.revolvingweekview.DayTime;

public class Lesson {
    @SerializedName("classNo")
    private final String classNo;
    @SerializedName("startTime")
    private final String startTime;
    @SerializedName("endTime")
    private final String endTime;
    @SerializedName("weeks")
    private final Weeks weeks;
    @SerializedName("venue")
    private final String venue;
    @SerializedName("day")
    private final String day;
    @SerializedName("lessonType")
    private final String lessonType;
    @SerializedName("size")
    private final int size;

    public Lesson(String classNo, String startTime, String endTime, String venue, String day,
                  String lessonType, int size, Weeks weeks) {
        this.classNo = classNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.day = day;
        this.lessonType = lessonType;
        this.size = size;
        this.weeks = weeks;
    }

    public int getSize() {
        return size;
    }

    public Weeks getWeeks() {
        return weeks;
    }

    public String getClassNo() {
        return classNo;
    }

    public DayOfWeek getDay() {
        switch (day) {
            case "Monday":
                return DayOfWeek.MONDAY;
            case "Tuesday":
                return DayOfWeek.TUESDAY;
            case "Wednesday":
                return DayOfWeek.WEDNESDAY;
            case "Thursday":
                return DayOfWeek.THURSDAY;
            case "Friday":
                return DayOfWeek.FRIDAY;
            case "Saturday":
                return DayOfWeek.SATURDAY;
            case "Sunday":
                return DayOfWeek.SUNDAY;
            default:
                throw new IllegalArgumentException("Invalid day");
        }
    }

    public DayTime getStartDayTime() {
        return new DayTime(getDay(), getStartHour(), getStartMinute());
    }

    public DayTime getEndDayTime() {
        return new DayTime(getDay(), getEndHour(), getEndMinute());
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Type getType() {
        return Type.fromString(lessonType);
    }

    public String getVenue() {
        return venue;
    }

    public int getStartHour() {
        return Integer.parseInt(getStartTime().substring(0, 2));
    }

    public int getStartMinute() {
        return Integer.parseInt(getStartTime().substring(2, 4));
    }

    public int getEndHour() {
        return Integer.parseInt(getEndTime().substring(0, 2));
    }

    public int getEndMinute() {
        return Integer.parseInt(getEndTime().substring(2, 4));
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + getType().getShortName() + "] " + classNo;
    }

    public boolean isOccurringEveryWeek() {
        return getWeeks().getWeeks().size() == 13;
    }

    public String getWeeksString() {
        StringJoiner joiner = new StringJoiner(", ");

        for (Number i : getWeeks().getWeeks()) {
            joiner.add(String.valueOf(i.intValue()));
        }

        return '[' + joiner.toString() + ']';
    }

    public enum Type {
        TUTORIAL("Tutorial", "TUT"),
        LECTURE("Lecture", "LEC"),
        PACKAGED_TUTORIAL("Packaged Tutorial", "PTUT"),
        PACKAGED_LECTURE("Packaged Lecture", "PLEC"),
        DESIGN_LECTURE("Design Lecture", "DLEC"),
        LAB("Laboratory", "LAB"),
        RECITATION("Recitation", "REC"),
        SECTIONAL("Sectional Teaching", "SEC");

        private static final Map<String, Type> nameMap;

        static {
            nameMap = new HashMap<>();

            for (Lesson.Type type : Type.values()) {
                nameMap.put(type.toString(), type);
            }
        }

        private final String name;
        private final String shortName;

        Type(String name, String shortName) {
            this.name = name;
            this.shortName = shortName;
        }

        public static Type fromString(String s) {
            Type type = nameMap.get(s);

            if (type == null) {
                throw new IllegalArgumentException("Invalid lesson type: " + s);
            }

            return type;
        }

        public String getShortName() {
            return shortName;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
    }
}
