package com.ashuh.nusmoduleplanner.timetable.domain.usecase;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.common.domain.repository.PreferencesRepository;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

public class GetColorSchemeUseCase {
    @NonNull
    private final PreferencesRepository preferenceRepository;

    public GetColorSchemeUseCase(@NonNull PreferencesRepository preferenceRepository) {
        this.preferenceRepository = requireNonNull(preferenceRepository);
    }

    @NonNull
    public LiveData<ColorScheme> execute() {
        return preferenceRepository.getColorScheme();
    }
}
