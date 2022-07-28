package com.ashuh.nusmoduleplanner.settings;

import static com.ashuh.nusmoduleplanner.common.data.repository.AppPreferencesRepository.PREFERENCE_FILE_KEY;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

import java.util.Arrays;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.clear();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName(PREFERENCE_FILE_KEY);
        setPreferencesFromResource(R.xml.preferences, rootKey);
        ListPreference colorSchemePreference = findPreference("color_scheme");
        assert colorSchemePreference != null;

        ColorScheme[] colorSchemes = ColorScheme.values();
        CharSequence[] entries = Arrays.stream(colorSchemes)
                .map(ColorScheme::toString)
                .toArray(CharSequence[]::new);
        CharSequence[] entryValues = Arrays.stream(colorSchemes)
                .map(ColorScheme::name)
                .toArray(CharSequence[]::new);
        colorSchemePreference.setEntries(entries);
        colorSchemePreference.setEntryValues(entryValues);
    }
}
