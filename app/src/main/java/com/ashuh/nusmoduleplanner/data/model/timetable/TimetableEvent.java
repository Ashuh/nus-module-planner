package com.ashuh.nusmoduleplanner.data.model.timetable;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.Lesson;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TimetableEvent(AssignedModule assignedModule, Lesson lesson, SemesterType semType,
                          me.jlurena.revolvingweekview.DayTime startTime,
                          me.jlurena.revolvingweekview.DayTime endTime) {

        super(assignedModule.getModuleCode() + lesson.getClassNo(),
                assignedModule.getModuleCode() + '\n' + lesson,
                lesson.getVenue(), startTime, endTime);

        this.assignedModule = assignedModule;
        this.lesson = lesson;
        this.semType = semType;
        altLessonsCodes = new TreeSet<>();

        for (Lesson l : assignedModule.getSemesterDatum().getTimetable(lesson.getType())) {
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
