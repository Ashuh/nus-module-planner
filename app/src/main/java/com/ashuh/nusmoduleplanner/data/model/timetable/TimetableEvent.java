package com.ashuh.nusmoduleplanner.data.model.timetable;

import com.ashuh.nusmoduleplanner.data.model.module.Lesson;
import com.ashuh.nusmoduleplanner.data.model.module.Semester;
import com.ashuh.nusmoduleplanner.data.model.module.Timetable;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import me.jlurena.revolvingweekview.WeekViewEvent;

public class TimetableEvent extends WeekViewEvent {
    private final Timetable timetable;
    private final Semester semType;
    private final SortedSet<String> altLessonsCodes;
    private final Lesson lesson;

    public TimetableEvent(Timetable timetable, Lesson lesson, Semester semType,
                          me.jlurena.revolvingweekview.DayTime startTime,
                          me.jlurena.revolvingweekview.DayTime endTime) {

        super(timetable.getModuleCode() + lesson.getClassNo(),
                timetable.getModuleCode() + '\n' + lesson,
                lesson.getVenue(), startTime, endTime);

        this.timetable = timetable;
        this.lesson = lesson;
        this.semType = semType;
        altLessonsCodes = new TreeSet<>();

        for (Lesson l : timetable.getLessons(lesson.getType())) {
            if (!lesson.getClassNo().equals(l.getClassNo())) {
                altLessonsCodes.add(l.getClassNo());
            }
        }
    }

    public String getModuleCode() {
        return timetable.getModuleCode();
    }

    public Lesson getLesson() {
        return lesson;
    }

    public Semester getSemType() {
        return semType;
    }

    public List<String> getAlternateLessonCodes() {
        return new ArrayList<>(altLessonsCodes);
    }
}
