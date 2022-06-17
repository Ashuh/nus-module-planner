package com.ashuh.nusmoduleplanner.common.data.remote.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashuh.nusmoduleplanner.common.data.remote.model.module.weeks.ApiWeeksResponse;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Weeks;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonOccurrence;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;
import com.google.gson.annotations.SerializedName;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ApiLesson {
    private static final String LESSON_TIME_FORMAT = "HHmm";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
            LESSON_TIME_FORMAT);

    @NonNull
    @SerializedName("classNo")
    private final String classNo;
    @NonNull
    @SerializedName("startTime")
    private final String startTime;
    @NonNull
    @SerializedName("endTime")
    private final String endTime;
    @NonNull
    @SerializedName("weeks")
    private final ApiWeeksResponse weeks;
    @NonNull
    @SerializedName("venue")
    private final String venue;
    @NonNull
    @SerializedName("day")
    private final String day;
    @NonNull
    @SerializedName("lessonType")
    private final String lessonType;
    @Nullable
    @SerializedName("size")
    private final Integer size;

    public ApiLesson(@NonNull String classNo, @NonNull String startTime, @NonNull String endTime,
                     @NonNull ApiWeeksResponse weeks, @NonNull String venue, @NonNull String day,
                     @NonNull String lessonType, @Nullable Integer size) {
        requireNonNull(classNo);
        requireNonNull(startTime);
        requireNonNull(endTime);
        requireNonNull(weeks);
        requireNonNull(venue);
        requireNonNull(day);
        requireNonNull(lessonType);
        this.classNo = classNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weeks = weeks;
        this.venue = venue;
        this.day = day;
        this.lessonType = lessonType;
        this.size = size;
    }

    @NonNull
    public String getClassNo() {
        return classNo;
    }

    @NonNull
    public String getStartTime() {
        return startTime;
    }

    @NonNull
    public String getEndTime() {
        return endTime;
    }

    @NonNull
    public ApiWeeksResponse getWeeks() {
        return weeks;
    }

    @NonNull
    public String getVenue() {
        return venue;
    }

    @NonNull
    public String getDay() {
        return day;
    }

    @NonNull
    public String getLessonType() {
        return lessonType;
    }

    @NonNull
    public Lesson toDomain() {
        LessonType domainLessonType = mapLessonType(lessonType);
        LessonOccurrence occurrence = mapLessonOccurrence();
        return new Lesson(classNo, domainLessonType, getSize().orElse(0), occurrence);
    }

    private LessonType mapLessonType(String apiLessonType) {
        switch (apiLessonType) {
            case "Tutorial":
                return LessonType.TUTORIAL;
            case "Packaged Tutorial":
                return LessonType.PACKAGED_TUTORIAL;
            case "Lecture":
                return LessonType.LECTURE;
            case "Packaged Lecture":
                return LessonType.PACKAGED_LECTURE;
            case "Design Lecture":
                return LessonType.DESIGN_LECTURE;
            case "Laboratory":
                return LessonType.LAB;
            case "Recitation":
                return LessonType.RECITATION;
            case "Sectional Teaching":
                return LessonType.SECTIONAL;
            default:
                throw new IllegalArgumentException("Invalid lesson type: " + apiLessonType);
        }
    }

    private LessonOccurrence mapLessonOccurrence() {
        DayOfWeek domainDay = DayOfWeek.valueOf(day.toUpperCase());
        LocalTime domainStartTime = LocalTime.parse(startTime, FORMATTER);
        LocalTime domainEndTime = LocalTime.parse(endTime, FORMATTER);
        Weeks domainWeeks = weeks.toDomain();
        return new LessonOccurrence(domainDay, domainStartTime, domainEndTime, domainWeeks, venue);
    }

    @NonNull
    public Optional<Integer> getSize() {
        return Optional.ofNullable(size);
    }
}
