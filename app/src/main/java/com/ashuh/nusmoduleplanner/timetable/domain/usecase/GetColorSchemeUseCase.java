package com.ashuh.nusmoduleplanner.timetable.domain.usecase;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.common.data.preferences.SharedPreferencesManager;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

public class GetColorSchemeUseCase {
    @NonNull
    private final SharedPreferencesManager preferenceRepository;

    public GetColorSchemeUseCase(@NonNull SharedPreferencesManager preferenceRepository) {
        this.preferenceRepository = requireNonNull(preferenceRepository);
    }

    @NonNull
    public LiveData<ColorScheme> execute() {
        return preferenceRepository.getColorScheme();
    }
}
