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

public class TimetableEvent extends WeekViewEvent {
    private static final String EVENT_NAME_FORMAT = "%s\n[%s] %s";

    private final String moduleCode;
    private final LessonType lessonType;
    private final String lessonNo;

    private TimetableEvent(String id, String name, String location, DayTime startTime,
                           DayTime endTime, String moduleCode, LessonType lessonType,
                           String lessonNo) {
        super(id, name, location, startTime, endTime);
        this.moduleCode = moduleCode;
        this.lessonType = lessonType;
        this.lessonNo = lessonNo;
    }

    public static List<TimetableEvent> fromTimetableEntry(@NonNull ModuleReading entry) {
        requireNonNull(entry);
        String moduleCode = entry.getModule().getModuleCode();
        Color color = entry.getColor();

        return entry.getAssignedLessons().stream()
                .map(lesson -> lesson.getOccurrences().stream()
                        .map(occurrence -> TimetableEvent.fromDomainTypes(moduleCode,
                                lesson, occurrence, color))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static TimetableEvent fromDomainTypes(String moduleCode, Lesson lesson,
                                                 LessonOccurrence occurrence, Color color) {
        String id = moduleCode + lesson.getLessonNo() + occurrence; // TODO: use a better id

        me.jlurena.revolvingweekview.DayTime startTime
                = convertTime(occurrence.getDay(), occurrence.getStartTime());
        me.jlurena.revolvingweekview.DayTime endTime
                = convertTime(occurrence.getDay(), occurrence.getEndTime());

        String name = String.format(EVENT_NAME_FORMAT, moduleCode,
                lesson.getLessonType().getShortName(), lesson.getLessonNo());

        TimetableEvent event = new TimetableEvent(id, name, occurrence.getVenue(),
                startTime, endTime, moduleCode, lesson.getLessonType(), lesson.getLessonNo());
        event.setColor(color.toArgb());
        return event;
    }

    public static me.jlurena.revolvingweekview.DayTime convertTime(DayOfWeek day, LocalTime time) {
        org.threeten.bp.DayOfWeek threetenDay = org.threeten.bp.DayOfWeek.valueOf(day.name());
        org.threeten.bp.LocalTime threetenTime
                = org.threeten.bp.LocalTime.of(time.getHour(), time.getMinute());
        return new me.jlurena.revolvingweekview.DayTime(threetenDay, threetenTime);
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public String getLessonNo() {
        return lessonNo;
    }
}
