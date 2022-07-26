package com.ashuh.nusmoduleplanner.common.domain.repository;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

public interface PreferencesRepository {
    @NonNull
    ColorScheme getColorScheme();

    void setColorScheme(@NonNull ColorScheme colorScheme);
}
