package com.ashuh.nusmoduleplanner.common.data.preferences;

import static java.util.Objects.requireNonNull;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.preference.Preference;

/**
 * A {@link LiveData} that represents a {@link Preference} value.
 * <p>
 * Adapted from
 * <a href=https://gist.github.com/idish/f46a8327da7f293f943a5bda31078c95>this gist</a>.
 */
public abstract class SharedPreferenceLiveData<T> extends LiveData<T>
        implements OnSharedPreferenceChangeListener {
    @NonNull
    protected final SharedPreferences sharedPrefs;
    @NonNull
    protected final String key;
    @Nullable
    protected final T defValue;

    public SharedPreferenceLiveData(@NonNull SharedPreferences prefs, @NonNull String key,
                                    @Nullable T defValue) {
        this.sharedPrefs = requireNonNull(prefs);
        this.key = requireNonNull(key);
        this.defValue = defValue;
    }

    @Override
    protected void onActive() {
        super.onActive();
        setValue(getValueFromPreferences());
        sharedPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    protected abstract T getValueFromPreferences();

    @Override
    protected void onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(this);
        super.onInactive();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (this.key.equals(key)) {
            setValue(getValueFromPreferences());
        }
    }
}
