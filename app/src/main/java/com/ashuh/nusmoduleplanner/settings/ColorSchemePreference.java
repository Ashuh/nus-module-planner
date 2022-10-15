package com.ashuh.nusmoduleplanner.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.gridlayout.widget.GridLayout;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme;
import com.ashuh.nusmoduleplanner.timetable.presentation.TimetableView;

public class ColorSchemePreference extends Preference {
    private static final int MARGIN_DP = 4;

    private final TimetableSampleDataLoader loader = new TimetableSampleDataLoader();
    private ColorScheme colorScheme;

    public ColorSchemePreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Nullable
    @Override
    protected Object onGetDefaultValue(@NonNull TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    public void onBindViewHolder(@NonNull PreferenceViewHolder holder) {
        TimetableView timetableView = (TimetableView) holder.findViewById(R.id.timetable);
        GridLayout gridLayout = (GridLayout) holder.findViewById(R.id.grid);

        if (gridLayout.getChildCount() == 0) {
            timetableView.setWeekViewLoader(loader);

            for (int i = 0; i < ColorScheme.values().length; i++) {
                ColorScheme colorScheme = ColorScheme.values()[i];
                ColorSchemeView colorSchemeView = generateColorSchemeView(colorScheme, i);
                gridLayout.addView(colorSchemeView);
            }
        } else {
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                ColorSchemeView child = (ColorSchemeView) gridLayout.getChildAt(i);
                child.setSelected(this.colorScheme == child.getColorScheme());
            }
        }

        loader.setColorScheme(colorScheme);
        timetableView.notifyDatasetChanged();
    }

    @Override
    protected void onSetInitialValue(@Nullable Object defaultValue) {
        ColorScheme colorScheme = ColorScheme.valueOf(getPersistedString((String) defaultValue));
        setColorScheme(colorScheme);
    }

    private ColorSchemeView generateColorSchemeView(ColorScheme colorScheme, int index) {
        ColorSchemeView colorSchemeView = new ColorSchemeView(getContext());
        colorSchemeView.setColorScheme(colorScheme);
        colorSchemeView.setLayoutParams(getLayoutParams(index));
        colorSchemeView.setSelected(this.colorScheme == colorScheme);
        colorSchemeView.setOnClickListener(v -> setColorScheme(colorScheme));
        return colorSchemeView;
    }

    private GridLayout.LayoutParams getLayoutParams(int index) {
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.columnSpec = GridLayout.spec(index % 2, GridLayout.FILL, 1);
        param.rowSpec = GridLayout.spec(index / 2, GridLayout.FILL, 1);
        param.setGravity(Gravity.FILL_HORIZONTAL);
        int marginPx = getMarginPx();
        param.setMargins(marginPx, marginPx, marginPx, marginPx);
        return param;
    }

    private void setColorScheme(@NonNull ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
        persistString(colorScheme.name());
        notifyChanged();
    }

    private int getMarginPx() {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                MARGIN_DP,
                getContext().getResources().getDisplayMetrics()
        );
    }
}
