package com.ashuh.nusmoduleplanner.common.data.repository;

import static java.util.Objects.requireNonNull;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ashuh.nusmoduleplanner.common.domain.repository.PreferencesRepository;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

public class AppPreferencesRepository implements PreferencesRepository {
    public static final String PREFERENCE_FILE_KEY = "com.ashuh.nusmoduleplanner.preferences";
    private static final String COLOR_SCHEME_KEY = "color_scheme";

    private final SharedPreferences sharedPreferences;
    static SharedPreferences.OnSharedPreferenceChangeListener listener;

    public AppPreferencesRepository(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = requireNonNull(sharedPreferences);
    }

    @NonNull
    @Override
    public LiveData<ColorScheme> getColorScheme() {
        String colorSchemeName = sharedPreferences
                .getString(COLOR_SCHEME_KEY, ColorScheme.GOOGLE.name());
        MutableLiveData<ColorScheme> observableColorScheme
                = new MutableLiveData<>(ColorScheme.valueOf(colorSchemeName));

        listener = (sharedPreferences, key) -> {
            if (key.equals(COLOR_SCHEME_KEY)) {
                String newColorScheme = sharedPreferences.getString(COLOR_SCHEME_KEY,
                        ColorScheme.GOOGLE.name());
                observableColorScheme.setValue(ColorScheme.valueOf(newColorScheme));
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);

        return observableColorScheme;
    }

    @Override
    public void setColorScheme(@NonNull ColorScheme colorScheme) {
        sharedPreferences.edit().putString(COLOR_SCHEME_KEY, colorScheme.name()).apply();
    }
}
