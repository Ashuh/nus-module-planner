package com.ashuh.nusmoduleplanner.data.model.timetable;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.BaseModule;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.ModuleSemesterDatum;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.Lesson;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.LessonType;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity(primaryKeys = {"semType", "moduleCode"})
public class AssignedModule extends BaseModule {

    @NonNull
    private final List<Lesson> timetable;
    private final ZonedDateTime examDate;
    private final int examDuration;
    @NonNull
    private final SemesterType semType;
    private final double moduleCredit;
    private Map<LessonType, String> assignedLessons;

    public AssignedModule(@NonNull String moduleCode, @NonNull String title, double moduleCredit,
                          @NonNull SemesterType semType,
                          @NonNull ModuleSemesterDatum semesterDatum) {
        super(moduleCode, title);
        Objects.requireNonNull(semType);
        Objects.requireNonNull(semesterDatum);
        this.semType = semType;
        this.timetable = semesterDatum.getTimetable();
        this.examDate = semesterDatum.getExamDate();
        this.examDuration = semesterDatum.getExamDuration();
        this.moduleCredit = moduleCredit;
        assignedLessons = new HashMap<>();

        for (Lesson lesson : semesterDatum.getTimetable()) {
            LessonType lessonType = lesson.getType();
            assignedLessons.put(lessonType, getTimetable(lessonType).get(0)
                    .getClassNo());
        }
    }

    public List<Lesson> getTimetable(LessonType lessonType) {
        return timetable.stream().filter((lesson) -> lesson.getType() == lessonType).collect(
                Collectors.toList());
    }

    public AssignedModule(@NonNull String moduleCode, @NonNull String title,
                          @NonNull List<Lesson> timetable, ZonedDateTime examDate, int examDuration,
                          @NonNull SemesterType semType, double moduleCredit) {
        super(moduleCode, title);
        this.timetable = timetable;
        this.examDate = examDate;
        this.examDuration = examDuration;
        this.semType = semType;
        this.moduleCredit = moduleCredit;
    }

    @NonNull
    public List<Lesson> getTimetable() {
        return timetable;
    }

    public List<Lesson> getTimetable(LessonType lessonType, String classNo) {
        return timetable.stream()
                .filter((lesson) -> lesson.getType() == lessonType && lesson.getClassNo()
                        .equals(classNo)).collect(Collectors.toList());
    }

    public ZonedDateTime getExamDate() {
        return examDate;
    }

    public int getExamDuration() {
        return examDuration;
    }

    public void setAssignedLesson(LessonType lessonType, String lessonCode) {
        boolean isValid = false;

        for (Lesson l : timetable) {
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

    public double getModuleCredit() {
        return moduleCredit;
    }
}
