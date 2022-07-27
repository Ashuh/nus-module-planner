package com.ashuh.nusmoduleplanner.timetable.presentation;

import static java.util.Objects.requireNonNull;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.GetColorSchemeUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.UpdateColorUseCase;

import java.util.List;
import java.util.stream.Collectors;

public class ColorSelectViewModel extends ViewModel {
    @NonNull
    private final GetColorSchemeUseCase getColorSchemeUseCase;
    @NonNull
    private final UpdateColorUseCase updateColorUseCase;

    public ColorSelectViewModel(@NonNull GetColorSchemeUseCase getColorSchemeUseCase,
                                @NonNull UpdateColorUseCase updateColorUseCase) {
        this.getColorSchemeUseCase = requireNonNull(getColorSchemeUseCase);
        this.updateColorUseCase = requireNonNull(updateColorUseCase);
    }

    public LiveData<List<Integer>> getSelectedColorSchemeColors() {
        return Transformations.map(getColorSchemeUseCase.execute(),
                colorScheme -> colorScheme.getColors().stream()
                        .map(Color::toArgb)
                        .collect(Collectors.toList()));
    }

    public void updateColor(@NonNull String moduleCode, int semester, int newColorId) {
        Semester domainSemester = Semester.fromInt(semester);
        updateColorUseCase.execute(moduleCode, domainSemester, newColorId);
    }
}
