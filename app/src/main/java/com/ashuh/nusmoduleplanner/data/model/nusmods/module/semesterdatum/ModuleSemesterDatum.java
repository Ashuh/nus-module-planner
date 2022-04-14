package com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum;

import static java.util.Objects.requireNonNull;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.Lesson;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.LessonType;

import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.O)
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

    public void setTimetable(@NonNull List<Lesson> timetable) {
        this.timetable = timetable;
    }

    public List<Lesson> getTimetable(LessonType lessonType) {
        return timetable.stream().filter((lesson) -> lesson.getType() == lessonType).collect(
                Collectors.toList());
    }

    public List<Lesson> getTimetable(LessonType lessonType, String classNo) {
        return timetable.stream()
                .filter((lesson) -> lesson.getType() == lessonType && lesson.getClassNo()
                        .equals(classNo)).collect(Collectors.toList());
    }
}
