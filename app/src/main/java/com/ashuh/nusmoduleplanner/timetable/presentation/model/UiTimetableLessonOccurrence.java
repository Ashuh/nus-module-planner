package com.ashuh.nusmoduleplanner.timetable.presentation.model;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import me.jlurena.revolvingweekview.DayTime;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class UiTimetableLessonOccurrence extends WeekViewEvent {
    private static int id = 0;

    @NonNull
    private final String moduleCode;
    @NonNull
    private final LessonType lessonType;
    @NonNull
    private final String lessonNo;

    public UiTimetableLessonOccurrence(String name, String location, DayTime startTime,
                                       DayTime endTime, @NonNull String moduleCode,
                                       @NonNull LessonType lessonType, @NonNull String lessonNo) {
        super(generateId(), name, location, startTime, endTime);
        this.moduleCode = requireNonNull(moduleCode);
        this.lessonType = requireNonNull(lessonType);
        this.lessonNo = requireNonNull(lessonNo);
    }

    private static String generateId() {
        return String.valueOf(id++);
    }

    @NonNull
    public String getModuleCode() {
        return moduleCode;
    }

    @NonNull
    public LessonType getLessonType() {
        return lessonType;
    }

    @NonNull
    public String getLessonNo() {
        return lessonNo;
    }
}
