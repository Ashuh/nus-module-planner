package com.ashuh.nusmoduleplanner.timetable;

import com.ashuh.nusmoduleplanner.data.module.Lesson;
import com.ashuh.nusmoduleplanner.data.module.SemesterType;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import me.jlurena.revolvingweekview.WeekViewEvent;

public class TimetableEvent extends WeekViewEvent {
    private final AssignedModule assignedModule;
    private final SemesterType semType;
    private final SortedSet<String> altLessonsCodes;
    private final Lesson lesson;

    public TimetableEvent(AssignedModule assignedModule, Lesson lesson, SemesterType semType,
                          me.jlurena.revolvingweekview.DayTime startTime,
                          me.jlurena.revolvingweekview.DayTime endTime) {

        super(assignedModule.getModuleDetail().getModuleCode() + lesson.getClassNo(),
                assignedModule.getModuleDetail().getModuleCode() + '\n' + lesson,
                lesson.getVenue(), startTime, endTime);

        this.assignedModule = assignedModule;
        this.lesson = lesson;
        this.semType = semType;
        altLessonsCodes = new TreeSet<>();

        for (Lesson l : assignedModule.getModuleDetail().getSemesterDetail(semType)
                .getLessons(lesson.getType())) {
            if (!lesson.getClassNo().equals(l.getClassNo())) {
                altLessonsCodes.add(l.getClassNo());
            }
        }
    }

    public AssignedModule getAssignedModule() {
        return assignedModule;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public SemesterType getSemType() {
        return semType;
    }

    public List<String> getAlternateLessonCodes() {
        return new ArrayList<>(altLessonsCodes);
    }
}
