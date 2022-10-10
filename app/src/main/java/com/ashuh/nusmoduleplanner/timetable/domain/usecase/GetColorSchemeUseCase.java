package com.ashuh.nusmoduleplanner.timetable.domain.usecase;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.common.data.preferences.SharedPreferencesManager;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

public class GetColorSchemeUseCase {
    @NonNull
    private final SharedPreferencesManager sharedPreferencesManager;

    public GetColorSchemeUseCase(@NonNull SharedPreferencesManager sharedPreferencesManager) {
        this.sharedPreferencesManager = requireNonNull(sharedPreferencesManager);
    }

    @NonNull
    public LiveData<ColorScheme> execute() {
        return sharedPreferencesManager.getColorScheme();
    }
}
