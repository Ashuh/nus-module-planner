package com.ashuh.nusmoduleplanner.data.source.timetable;

import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.LessonType;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;

import java.util.List;

public class TimetableDataSource {

    private final TimetableDAO dao;

    public TimetableDataSource(TimetableDAO dao) {
        this.dao = dao;
    }

    public void insert(AssignedModule entry) {
        dao.insert(entry);
    }

    public LiveData<List<AssignedModule>> getSemesterAssignedModules(SemesterType sem) {
        return dao.getAssignedModules(sem);
    }

    public void delete(SemesterType semType, String moduleCode) {
        dao.delete(semType, moduleCode);
    }

    public void updateAssignedLesson(String moduleCode, SemesterType semType,
                                     LessonType lessonType, String newLessonCode) {
        AssignedModule assignedModule = getAssignedModule(moduleCode, semType);
        assignedModule.setAssignedLesson(lessonType, newLessonCode);
        dao.update(assignedModule);
    }

    public AssignedModule getAssignedModule(String moduleCode, SemesterType semType) {
        return dao.getAssignedModule(moduleCode, semType);
    }
}
