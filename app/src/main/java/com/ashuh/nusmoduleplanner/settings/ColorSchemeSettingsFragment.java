package com.ashuh.nusmoduleplanner.settings;

import static com.ashuh.nusmoduleplanner.common.data.preferences.SharedPreferencesManager.PREFERENCE_FILE_KEY;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.ashuh.nusmoduleplanner.R;

public class ColorSchemeSettingsFragment extends NoMenuPreferenceFragment {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName(PREFERENCE_FILE_KEY);
        setPreferencesFromResource(R.xml.color_scheme_preferences, rootKey);
    }
}
