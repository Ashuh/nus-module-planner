package com.ashuh.nusmoduleplanner.timetable.presentation.model;

import static java.util.Objects.requireNonNull;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonOccurrence;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import me.jlurena.revolvingweekview.DayTime;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class UiTimetableLessonOccurrence extends WeekViewEvent {
    private static final String EVENT_NAME_FORMAT = "%s\n[%s] %s";
    private static int id = 0;

    @NonNull
    private final String moduleCode;
    @NonNull
    private final LessonType lessonType;
    @NonNull
    private final String lessonNo;

    private UiTimetableLessonOccurrence(String id, String name, String location, DayTime startTime,
                                        DayTime endTime, @NonNull String moduleCode,
                                        @NonNull LessonType lessonType, @NonNull String lessonNo) {
        super(id, name, location, startTime, endTime);
        requireNonNull(moduleCode);
        requireNonNull(lessonType);
        requireNonNull(lessonNo);
        this.moduleCode = moduleCode;
        this.lessonType = lessonType;
        this.lessonNo = lessonNo;
    }

    public static List<UiTimetableLessonOccurrence> fromModuleReading(
            @NonNull ModuleReading moduleReading) {
        String moduleCode = moduleReading.getModule().getModuleCode();
        Color color = moduleReading.getColor();

        return moduleReading.getAssignedLessons().stream()
                .map(lesson -> lesson.getOccurrences().stream()
                        .map(occurrence -> UiTimetableLessonOccurrence.fromDomainTypes(moduleCode,
                                lesson, occurrence, color))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static UiTimetableLessonOccurrence fromDomainTypes(@NonNull String moduleCode,
                                                              @NonNull Lesson lesson,
                                                              @NonNull LessonOccurrence occurrence,
                                                              @NonNull Color color) {
        String id = generateId();
        DayTime startTime = convertTime(occurrence.getDay(), occurrence.getStartTime());
        DayTime endTime = convertTime(occurrence.getDay(), occurrence.getEndTime());
        String name = String.format(EVENT_NAME_FORMAT, moduleCode,
                lesson.getLessonType().getShortName(), lesson.getLessonNo());

        UiTimetableLessonOccurrence event = new UiTimetableLessonOccurrence(id, name,
                occurrence.getVenue(),
                startTime, endTime, moduleCode, lesson.getLessonType(), lesson.getLessonNo());
        event.setColor(color.toArgb());
        return event;
    }

    private static String generateId() {
        return String.valueOf(id++);
    }

    private static DayTime convertTime(DayOfWeek day, LocalTime time) {
        org.threeten.bp.DayOfWeek threetenDay = org.threeten.bp.DayOfWeek.valueOf(day.name());
        org.threeten.bp.LocalTime threetenTime
                = org.threeten.bp.LocalTime.of(time.getHour(), time.getMinute());
        return new DayTime(threetenDay, threetenTime);
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
