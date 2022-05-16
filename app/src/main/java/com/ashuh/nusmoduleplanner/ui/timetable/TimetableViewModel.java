package com.ashuh.nusmoduleplanner.ui.timetable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.LessonType;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.source.timetable.TimetableDataSource;

import java.util.List;

public class TimetableViewModel extends ViewModel {

    private final TimetableDataSource dataSource;
    private final LiveData<List<AssignedModule>> data;

    public TimetableViewModel(TimetableDataSource timetableDataSource, SemesterType sem) {
        this.dataSource = timetableDataSource;
        data = timetableDataSource.getSemesterAssignedModules(sem);
    }

    public LiveData<List<AssignedModule>> getTimetableEntriesObservable() {
        return data;
    }

    public void updateAssignedLesson(String moduleCode, SemesterType semType, LessonType lessonType,
                                     String selectedLessonCode) {
        dataSource.updateAssignedLesson(moduleCode, semType, lessonType,
                selectedLessonCode);
    }

    public AssignedModule getAssignedModule(String moduleCode, SemesterType semType) {
        return dataSource.getAssignedModule(moduleCode, semType);
    }

    public void delete(SemesterType semType, String moduleCode) {
        dataSource.delete(semType, moduleCode);
    }
}
