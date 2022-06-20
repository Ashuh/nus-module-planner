package com.ashuh.nusmoduleplanner.timetable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableViewModel extends ViewModel {

    private final ModuleRepository moduleRepository;
    private final LiveData<List<ModuleReading>> data;

    public TimetableViewModel(ModuleRepository moduleRepository, Semester semester) {
        this.moduleRepository = moduleRepository;
        data = moduleRepository.getModuleReadings(semester);
    }

    public LiveData<List<ModuleReading>> getTimetableEntriesObservable() {
        return data;
    }

    public void updateAssignedLesson(String moduleCode, Semester semester, LessonType lessonType,
                                     String newLessonNo) {
        moduleRepository.updateLessonNoMapping(moduleCode, semester, lessonType,
                newLessonNo);
    }

    public void deleteTimetableEntry(String moduleCode, Semester semester) {
        moduleRepository.deleteModuleReading(moduleCode, semester);
    }

    public LiveData<List<Lesson>> getLessons(String moduleCode, Semester semester,
                                             LessonType lessonType) {
        return Transformations.map(
                moduleRepository.getModule(AcademicYear.getCurrent().toString(), moduleCode),
                module -> module.getSemesterDatum(semester)
                        .map(datum -> datum.getLessons(lessonType))
                        .orElseThrow(() -> new IllegalStateException("No semester data")));
    }
}
