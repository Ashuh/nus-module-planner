package com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.Lesson;

import java.util.List;

public class ModuleSemesterDatum extends ModuleInformationSemesterDatum {

    @NonNull
    private List<Lesson> timetable;

    public ModuleSemesterDatum(String examDate, int examDuration,
                               @NonNull SemesterType semester,
                               @NonNull List<Lesson> timetable) {
        super(examDate, examDuration, semester);
        requireNonNull(timetable);
        this.timetable = timetable;
    }

    @NonNull
    public List<Lesson> getTimetable() {
        return timetable;
    }
}
