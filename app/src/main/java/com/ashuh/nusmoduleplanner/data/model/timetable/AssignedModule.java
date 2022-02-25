package com.ashuh.nusmoduleplanner.data.model.timetable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ashuh.nusmoduleplanner.data.model.nusmods.Lesson;
import com.ashuh.nusmoduleplanner.data.model.nusmods.ModuleDetail;
import com.ashuh.nusmoduleplanner.data.model.nusmods.SemesterType;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "assigned_modules", primaryKeys = {"semType", "moduleCode"})
public class AssignedModule {
    @NonNull
    private SemesterType semType;

    @NonNull
    @Embedded
    private ModuleDetail moduleDetail;

    private Map<Lesson.Type, String> assignedLessons;

    public AssignedModule(SemesterType semType, ModuleDetail moduleDetail) {
        this.semType = semType;
        this.moduleDetail = moduleDetail;
        assignedLessons = new HashMap<>();

        for (Lesson lesson : moduleDetail.getSemesterDetail(semType).getTimetable()) {
            Lesson.Type lessonType = lesson.getType();
            assignedLessons
                    .put(lessonType,
                            moduleDetail.getSemesterDetail(semType).getLessons(lessonType).get(0)
                                    .getClassNo());
        }
    }

    public void setModule(ModuleDetail moduleDetail) {
        this.moduleDetail = moduleDetail;
    }

    public void setAssignedLesson(Lesson.Type lessonType, String lessonCode) {
        boolean isValid = false;

        for (Lesson l : moduleDetail.getSemesterDetail(semType).getLessons(lessonType)) {
            if (l.getClassNo().equals(lessonCode)) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            System.out.println("INVALID LESSON CODE");
            System.out.println("AVAILABLE: ");
            System.out.println(moduleDetail.getSemesterDetail(semType).getLessons(lessonType));
            return;
        } else {
            System.out.println("VALID");
        }

        assignedLessons.put(lessonType, lessonCode);
    }

    public SemesterType getSemType() {
        return semType;
    }

    public void setSemType(SemesterType semType) {
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
