package com.ashuh.nusmoduleplanner.timetable.presentation;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.DeleteModuleReadingUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.GetAlternateLessonsUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.GetModuleReadingsUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.UpdateLessonNoUseCase;

import org.jetbrains.annotations.NotNull;


public class TimetableViewModelFactory implements ViewModelProvider.Factory {
    private final ModuleRepository moduleRepository;
    private final Semester semester;

    public TimetableViewModelFactory(ModuleRepository moduleRepository, Semester semester) {
        this.moduleRepository = moduleRepository;
        this.semester = semester;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> aClass) {
        GetModuleReadingsUseCase getModuleReadingsUseCase
                = new GetModuleReadingsUseCase(moduleRepository);
        GetAlternateLessonsUseCase getAlternateLessonsUseCase
                = new GetAlternateLessonsUseCase(moduleRepository);
        UpdateLessonNoUseCase updateLessonNoUseCase = new UpdateLessonNoUseCase(moduleRepository);
        DeleteModuleReadingUseCase deleteModuleReadingUseCase
                = new DeleteModuleReadingUseCase(moduleRepository);
        return (T) new TimetableViewModel(getModuleReadingsUseCase, getAlternateLessonsUseCase,
                updateLessonNoUseCase, deleteModuleReadingUseCase, semester);
    }
}
