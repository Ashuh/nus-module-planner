package com.ashuh.nusmoduleplanner.settings;

import static java.util.Objects.requireNonNull;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

import java.util.List;
import java.util.stream.Collectors;

public class ColorSchemeView extends FrameLayout {
    private static final int BOX_SIZE_DP = 20;

    private ColorScheme colorScheme;

    public ColorSchemeView(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.color_scheme_layout, this);
        setClickable(true);
        setBackgroundResource(R.drawable.color_scheme_btn);
        TypedValue outValue = new TypedValue();
        context.getTheme()
                .resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
    }

    public ColorSchemeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ColorScheme getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(@NonNull ColorScheme colorScheme) {
        requireNonNull(colorScheme);
        this.colorScheme = colorScheme;

        List<Integer> colors = colorScheme.getColors().stream()
                .map(Color::toArgb)
                .collect(Collectors.toList());

        TextView nameView = findViewById(R.id.name);
        nameView.setText(colorScheme.toString());

        LinearLayout colorsView = findViewById(R.id.colors);
        colorsView.removeAllViews();

        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                BOX_SIZE_DP,
                getResources().getDisplayMetrics()
        );

        for (Integer s : colors) {
            View a = new View(getContext());
            a.setBackgroundColor(s);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(px, px);
            a.setLayoutParams(params);
            colorsView.addView(a);
        }
    }
}
