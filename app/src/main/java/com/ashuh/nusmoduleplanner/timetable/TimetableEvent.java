package com.ashuh.nusmoduleplanner.timetable;

import static java.util.Objects.requireNonNull;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonOccurrence;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.jlurena.revolvingweekview.DayTime;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class TimetableEvent extends WeekViewEvent {
    private static final String EVENT_NAME_FORMAT = "%s\n[%s] %s";

    private final String moduleCode;
    private final LessonType lessonType;
    private final String lessonNo;
    private final Set<String> allLessonNos;

    private TimetableEvent(String id, String name, String location, DayTime startTime,
                           DayTime endTime, String moduleCode, LessonType lessonType,
                           String lessonNo,
                           Set<String> allLessonNos) {
        super(id, name, location, startTime, endTime);
        this.moduleCode = moduleCode;
        this.lessonType = lessonType;
        this.lessonNo = lessonNo;
        this.allLessonNos = allLessonNos;
    }

    public static List<TimetableEvent> fromTimetableEntry(@NonNull ModuleReading entry) {
        requireNonNull(entry);
        String moduleCode = entry.getModule().getModuleCode();
        Color color = entry.getColor();

        List<TimetableEvent> events = new ArrayList<>();
        entry.getAssignedLessons().forEach(lesson -> {
            LessonType lessonType = lesson.getLessonType();
            Set<String> lessonNos = entry.getSemesterDatum().getLessonNos(lessonType);
            lesson.getOccurrences().stream()
                    .map(occurrence -> TimetableEvent.fromDomainTypes(moduleCode, lesson,
                            occurrence, lessonNos, color))
                    .forEach(events::add);
        });
        return events;
    }

    public static TimetableEvent fromDomainTypes(String moduleCode, Lesson lesson,
                                                 LessonOccurrence occurrence, Set<String> lessonNos,
                                                 Color color) {
        String id = moduleCode + lesson.getLessonNo() + occurrence; // TODO: use a better id

        me.jlurena.revolvingweekview.DayTime startTime
                = convertTime(occurrence.getDay(), occurrence.getStartTime());
        me.jlurena.revolvingweekview.DayTime endTime
                = convertTime(occurrence.getDay(), occurrence.getEndTime());

        String name = String.format(EVENT_NAME_FORMAT, moduleCode,
                lesson.getLessonType().getShortName(), lesson.getLessonNo());

        TimetableEvent event = new TimetableEvent(id, name, occurrence.getVenue(),
                startTime, endTime, moduleCode, lesson.getLessonType(),
                lesson.getLessonNo(), lessonNos);
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
