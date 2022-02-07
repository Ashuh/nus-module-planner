package com.ashuh.nusmoduleplanner.timetable;

import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.data.module.Lesson;
import com.ashuh.nusmoduleplanner.data.module.SemesterType;

import java.util.List;

public class TimetableDataSource {
    private static TimetableDataSource instance;
    private final TimetableDAO dao;

    TimetableDataSource() {
        dao = TimetableDatabase.getDatabase().dao();
    }

    public static TimetableDataSource getInstance() {
        if (instance == null) {
            instance = new TimetableDataSource();
        }

        return instance;
    }

    public void insert(AssignedModule entry) {
        dao.insert(entry);
    }

    public LiveData<List<AssignedModule>> getSemesterAssignedModules(SemesterType sem) {
        return dao.getAssignedModules(sem);
    }

    public AssignedModule getAssignedModule(String moduleCode, SemesterType semType) {
        return dao.getAssignedModule(moduleCode, semType);
    }

    public void delete(SemesterType semType, String moduleCode) {
        dao.delete(semType, moduleCode);
    }

    public void updateAssignedLesson(String moduleCode, SemesterType semType,
                                     Lesson.Type lessonType, String newLessonCode) {
        AssignedModule assignedModule = getAssignedModule(moduleCode, semType);
        assignedModule.setAssignedLesson(lessonType, newLessonCode);
        dao.update(assignedModule);
    }
}
