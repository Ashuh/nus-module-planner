package com.ashuh.nusmoduleplanner.common.domain.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

public interface PreferencesRepository {
    @NonNull
    LiveData<ColorScheme> getColorScheme();

    void setColorScheme(@NonNull ColorScheme colorScheme);
}
