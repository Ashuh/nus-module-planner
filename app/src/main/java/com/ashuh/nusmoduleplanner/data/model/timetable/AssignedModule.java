package com.ashuh.nusmoduleplanner.data.model.timetable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ashuh.nusmoduleplanner.data.model.module.Lesson;
import com.ashuh.nusmoduleplanner.data.model.module.ModuleDetail;
import com.ashuh.nusmoduleplanner.data.model.module.Semester;
import com.ashuh.nusmoduleplanner.data.model.module.Timetable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = "assigned_modules", primaryKeys = {"semType", "moduleCode"})
public class AssignedModule {
    @NonNull
    private Semester semType;

    @NonNull
    @Embedded
    private ModuleDetail moduleDetail;

    private Map<Lesson.Type, String> assignedLessons;

    public AssignedModule(Semester semType, ModuleDetail moduleDetail) {
        this.semType = semType;
        this.moduleDetail = moduleDetail;
        assignedLessons = new HashMap<>();
        List<Lesson> lessons = moduleDetail.getSemesterDetail(semType).getLessons();
        Timetable timetable = new Timetable(lessons, moduleDetail.getModuleCode(), semType);

        for (Lesson lesson : lessons) {
            Lesson.Type lessonType = lesson.getType();
            Lesson firstLesson = timetable.getLessons(lessonType).get(0);
            assignedLessons.put(lessonType, firstLesson.getClassNo());
        }
    }

    public void setModule(ModuleDetail moduleDetail) {
        this.moduleDetail = moduleDetail;
    }

    public void setAssignedLesson(Lesson.Type lessonType, String lessonCode) {
        assignedLessons.put(lessonType, lessonCode);
    }

    public Semester getSemType() {
        return semType;
    }

    public void setSemType(Semester semType) {
        this.semType = semType;
    }

    public ModuleDetail getModuleDetail() {
        return moduleDetail;
    }

    public Map<Lesson.Type, String> getAssignedLessons() {
        return assignedLessons;
    }

    public void setAssignedLessons(Map<Lesson.Type, String> assignedLessons) {
        this.assignedLessons = assignedLessons;
    }
}
