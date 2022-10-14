package com.ashuh.nusmoduleplanner.settings;

import static com.ashuh.nusmoduleplanner.common.data.preferences.SharedPreferencesManager.PREFERENCE_FILE_KEY;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import com.ashuh.nusmoduleplanner.R;

public class SettingsFragment extends NoMenuPreferenceFragment {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName(PREFERENCE_FILE_KEY);
        setPreferencesFromResource(R.xml.preferences, rootKey);

        Preference colorSchemePreference = findPreference("color_scheme");
        assert colorSchemePreference != null;
        colorSchemePreference.setOnPreferenceClickListener(preference -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_nav_settings_main_to_colorSchemeSettingsFragment);
            return true;
        });
    }
}
