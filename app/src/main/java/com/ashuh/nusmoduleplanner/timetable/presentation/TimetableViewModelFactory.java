package com.ashuh.nusmoduleplanner.timetable.presentation;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.common.data.preferences.SharedPreferencesManager;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.DeleteModuleReadingUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.GetAlternateLessonsUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.GetColorSchemeUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.GetModuleReadingsUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.UpdateLessonNoUseCase;

public class TimetableViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final ModuleRepository moduleRepository;
    @NonNull
    private final SharedPreferencesManager preferenceRepository;
    @NonNull
    private final Semester semester;

    public TimetableViewModelFactory(@NonNull ModuleRepository moduleRepository,
                                     @NonNull SharedPreferencesManager preferenceRepository,
                                     @NonNull Semester semester) {
        this.moduleRepository = requireNonNull(moduleRepository);
        this.preferenceRepository = requireNonNull(preferenceRepository);
        this.semester = requireNonNull(semester);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        GetModuleReadingsUseCase getModuleReadingsUseCase
                = new GetModuleReadingsUseCase(moduleRepository);
        GetAlternateLessonsUseCase getAlternateLessonsUseCase
                = new GetAlternateLessonsUseCase(moduleRepository);
        UpdateLessonNoUseCase updateLessonNoUseCase = new UpdateLessonNoUseCase(moduleRepository);
        DeleteModuleReadingUseCase deleteModuleReadingUseCase
                = new DeleteModuleReadingUseCase(moduleRepository);
        GetColorSchemeUseCase getColorSchemeUseCase
                = new GetColorSchemeUseCase(preferenceRepository);
        return (T) new TimetableViewModel(getModuleReadingsUseCase, getAlternateLessonsUseCase,
                updateLessonNoUseCase, deleteModuleReadingUseCase, getColorSchemeUseCase, semester);
    }
}
