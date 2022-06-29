package com.ashuh.nusmoduleplanner.timetable.presentation;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.DeleteModuleReadingUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.GetAlternateLessonsUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.GetModuleReadingsUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.UpdateLessonNoUseCase;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiLessonOccurrence;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiModuleReading;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableViewModel extends ViewModel {
    @NonNull
    private final GetModuleReadingsUseCase getModuleReadingsUseCase;
    @NonNull
    private final GetAlternateLessonsUseCase getAlternateLessonsUseCase;
    @NonNull
    private final UpdateLessonNoUseCase updateLessonNoUseCase;
    @NonNull
    private final DeleteModuleReadingUseCase deleteModuleReadingUseCase;
    @NonNull
    private final LiveData<TimetableState> observableState;
    @NonNull
    private final Semester semester;

    public TimetableViewModel(@NonNull GetModuleReadingsUseCase getModuleReadingsUseCase,
                              @NonNull GetAlternateLessonsUseCase getAlternateLessonsUseCase,
                              @NonNull UpdateLessonNoUseCase updateLessonNoUseCase,
                              @NonNull DeleteModuleReadingUseCase deleteModuleReadingUseCase,
                              @NonNull Semester semester) {
        requireNonNull(getModuleReadingsUseCase);
        requireNonNull(getAlternateLessonsUseCase);
        requireNonNull(updateLessonNoUseCase);
        requireNonNull(deleteModuleReadingUseCase);
        requireNonNull(semester);
        this.getModuleReadingsUseCase = getModuleReadingsUseCase;
        this.getAlternateLessonsUseCase = getAlternateLessonsUseCase;
        this.updateLessonNoUseCase = updateLessonNoUseCase;
        this.deleteModuleReadingUseCase = deleteModuleReadingUseCase;
        this.semester = semester;
        this.observableState = Transformations.map(getModuleReadingsUseCase.execute(semester),
                TimetableViewModel::buildState);

    }

    private static TimetableState buildState(Collection<ModuleReading> moduleReadings) {
        List<UiLessonOccurrence> uiLessonOccurrences = moduleReadings.stream()
                .map(UiLessonOccurrence::fromModuleReading)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        List<UiModuleReading> uiModuleReadings = moduleReadings.stream()
                .map(UiModuleReading::fromDomain)
                .collect(Collectors.toList());

        return new TimetableState(uiLessonOccurrences, uiModuleReadings);

    }

    @NonNull
    public LiveData<TimetableState> getState() {
        return observableState;
    }

    @NonNull
    public LiveData<List<Lesson>> getAlternateLessons(String moduleCode, LessonType lessonType,
                                                      String curLessonNo) {
        return getAlternateLessonsUseCase.execute(AcademicYear.getCurrent(), moduleCode, semester,
                lessonType, curLessonNo);
    }

    public void updateAssignedLessonNo(String moduleCode, LessonType lessonType,
                                       String newLessonNo) {
        updateLessonNoUseCase.execute(moduleCode, semester, lessonType, newLessonNo);
    }

    public void deleteModuleReading(String moduleCode) {
        deleteModuleReadingUseCase.execute(moduleCode, semester);
    }
}
