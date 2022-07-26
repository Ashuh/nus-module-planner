package com.ashuh.nusmoduleplanner.common.domain.repository;

import static java.util.Objects.requireNonNull;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

public class AppPreferencesRepository implements PreferencesRepository {
    public static final String PREFERENCE_FILE_KEY
            = "com.ashuh.nusmoduleplanner.PREFERENCE_FILE_KEY";
    private static final String COLOR_SCHEME_KEY
            = "color_scheme";

    private final SharedPreferences sharedPreferences;

    public AppPreferencesRepository(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = requireNonNull(sharedPreferences);
    }

    @NonNull
    @Override
    public ColorScheme getColorScheme() {
        String colorSchemeName = sharedPreferences
                .getString(COLOR_SCHEME_KEY, ColorScheme.GOOGLE.name());
        return ColorScheme.valueOf(colorSchemeName);
    }

    @Override
    public void setColorScheme(@NonNull ColorScheme colorScheme) {
        sharedPreferences.edit().putString(COLOR_SCHEME_KEY, colorScheme.name()).apply();
    }
}
