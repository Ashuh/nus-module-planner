package com.ashuh.nusmoduleplanner.common.data.preferences;

import android.content.SharedPreferences;

public class SharedPreferenceStringLiveData extends SharedPreferenceLiveData<String> {

    public SharedPreferenceStringLiveData(SharedPreferences prefs, String key, String defValue) {
        super(prefs, key, defValue);
    }

    @Override
    protected String getValueFromPreferences() {
        return sharedPrefs.getString(key, defValue);
    }
}
