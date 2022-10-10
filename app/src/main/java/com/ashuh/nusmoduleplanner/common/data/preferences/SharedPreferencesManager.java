package com.ashuh.nusmoduleplanner.common.data.preferences;

import static java.util.Objects.requireNonNull;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

public class SharedPreferencesManager {
    public static final String PREFERENCE_FILE_KEY = "com.ashuh.nusmoduleplanner.preferences";
    private static final String COLOR_SCHEME_KEY = "color_scheme";

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesManager(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = requireNonNull(sharedPreferences);
    }

    @NonNull
    public LiveData<ColorScheme> getColorScheme() {
        SharedPreferenceStringLiveData observableColorSchemeName
                = new SharedPreferenceStringLiveData(sharedPreferences, COLOR_SCHEME_KEY,
                ColorScheme.GOOGLE.name());
        return Transformations.map(observableColorSchemeName, ColorScheme::valueOf);
    }

    public void setColorScheme(@NonNull ColorScheme colorScheme) {
        sharedPreferences.edit().putString(COLOR_SCHEME_KEY, colorScheme.name()).apply();
    }
}
