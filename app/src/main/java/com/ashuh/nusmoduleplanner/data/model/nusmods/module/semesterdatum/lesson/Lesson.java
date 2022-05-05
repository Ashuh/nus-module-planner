package com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.weeks.Weeks;

import org.threeten.bp.DayOfWeek;

import me.jlurena.revolvingweekview.DayTime;

public class Lesson {

    @NonNull
    private final String classNo;
    @NonNull
    private final String startTime;
    @NonNull
    private final String endTime;
    @NonNull
    private final Weeks weeks;
    @NonNull
    private final String venue;
    @NonNull
    private final String day;
    @NonNull
    private final String lessonType;
    private final int size;

    public Lesson(@NonNull String classNo, @NonNull String startTime, @NonNull String endTime,
                  @NonNull Weeks weeks, @NonNull String venue, @NonNull String day,
                  @NonNull String lessonType, int size) {
        this.classNo = classNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weeks = weeks;
        this.venue = venue;
        this.day = day;
        this.lessonType = lessonType;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @NonNull
    public String getClassNo() {
        return classNo;
    }

    public DayTime getStartDayTime() {
        return new DayTime(getDay(), getStartHour(), getStartMinute());
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

    public int getStartHour() {
        return Integer.parseInt(getStartTime().substring(0, 2));
    }

    public int getStartMinute() {
        return Integer.parseInt(getStartTime().substring(2, 4));
    }

    @NonNull
    public String getStartTime() {
        return startTime;
    }

    public DayTime getEndDayTime() {
        return new DayTime(getDay(), getEndHour(), getEndMinute());
    }

    public int getEndHour() {
        return Integer.parseInt(getEndTime().substring(0, 2));
    }

    public int getEndMinute() {
        return Integer.parseInt(getEndTime().substring(2, 4));
    }

    @NonNull
    public String getEndTime() {
        return endTime;
    }

    @NonNull
    public String getVenue() {
        return venue;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + getType().getShortName() + "] " + classNo;
    }

    public LessonType getType() {
        return LessonType.fromString(lessonType);
    }

    @NonNull
    public Weeks getWeeks() {
        return weeks;
    }

    public String getScheduleDescription() {
        return weeks.toString();
    }
}
