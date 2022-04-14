package com.ashuh.nusmoduleplanner.data.model.timetable;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.BaseModule;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.ModuleSemesterDatum;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.Lesson;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.LessonType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity(tableName = "assigned_modules", primaryKeys = {"semType", "moduleCode"})
public class AssignedModule extends BaseModule {
    @NonNull
    private final ModuleSemesterDatum semesterDatum;
    @NonNull
    private final SemesterType semType;
    private final double moduleCredit;
    private Map<LessonType, String> assignedLessons;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public AssignedModule(@NonNull String moduleCode, @NonNull String title, double moduleCredit,
                          @NonNull SemesterType semType, @NonNull ModuleSemesterDatum semesterDatum) {
        super(moduleCode, title);
        Objects.requireNonNull(semType);
        Objects.requireNonNull(semesterDatum);
        this.semType = semType;
        this.semesterDatum = semesterDatum;
        this.moduleCredit = moduleCredit;
        assignedLessons = new HashMap<>();

        for (Lesson lesson : semesterDatum.getTimetable()) {
            LessonType lessonType = lesson.getType();
            assignedLessons.put(lessonType, semesterDatum.getTimetable(lessonType).get(0)
                    .getClassNo());
        }
    }

    public void setAssignedLesson(LessonType lessonType, String lessonCode) {
        boolean isValid = false;

        for (Lesson l : semesterDatum.getTimetable(lessonType)) {
            if (l.getClassNo().equals(lessonCode)) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            return;
        }

        assignedLessons.put(lessonType, lessonCode);
    }

    @NonNull
    public SemesterType getSemType() {
        return semType;
    }

    public Map<LessonType, String> getAssignedLessons() {
        return assignedLessons;
    }

    public void setAssignedLessons(Map<LessonType, String> assignedLessons) {
        this.assignedLessons = assignedLessons;
    }

    @NonNull
    public ModuleSemesterDatum getSemesterDatum() {
        return semesterDatum;
    }

    public double getModuleCredit() {
        return moduleCredit;
    }
}
