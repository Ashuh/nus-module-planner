package com.ashuh.nusmoduleplanner.data.model.timetable;

import com.ashuh.nusmoduleplanner.data.model.nusmods.Lesson;
import com.ashuh.nusmoduleplanner.data.model.nusmods.Semester;
import com.ashuh.nusmoduleplanner.data.model.nusmods.Timetable;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import me.jlurena.revolvingweekview.WeekViewEvent;

public class TimetableEvent extends WeekViewEvent {
    private final AssignedModule assignedModule;
    private final Semester semType;
    private final SortedSet<String> altLessonsCodes;
    private final Lesson lesson;

    public TimetableEvent(AssignedModule assignedModule, Lesson lesson, Semester semType,
                          me.jlurena.revolvingweekview.DayTime startTime,
                          me.jlurena.revolvingweekview.DayTime endTime) {

        super(assignedModule.getModuleDetail().getModuleCode() + lesson.getClassNo(),
                assignedModule.getModuleDetail().getModuleCode() + '\n' + lesson,
                lesson.getVenue(), startTime, endTime);

        this.assignedModule = assignedModule;
        this.lesson = lesson;
        this.semType = semType;
        altLessonsCodes = new TreeSet<>();
        List<Lesson> lessons = assignedModule.getModuleDetail().getSemesterDetail(semType).getLessons();
        Timetable timetable = new Timetable(lessons);
        for (Lesson l : timetable.getLessons(lesson.getType())) {
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

    public Semester getSemType() {
        return semType;
    }

    public List<String> getAlternateLessonCodes() {
        return new ArrayList<>(altLessonsCodes);
    }
}
