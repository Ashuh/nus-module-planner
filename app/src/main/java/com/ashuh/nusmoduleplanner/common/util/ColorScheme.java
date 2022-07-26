package com.ashuh.nusmoduleplanner.common.util;


import android.graphics.Color;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public enum ColorScheme {

    GOOGLE(Colors.GOOGLE_RED,
            Colors.GOOGLE_ORANGE,
            Colors.GOOGLE_YELLOW,
            Colors.GOOGLE_GREEN,
            Colors.GOOGLE_DARK_BLUE,
            Colors.GOOGLE_LIGHT_BLUE,
            Colors.GOOGLE_PURPLE,
            Colors.GOOGLE_PINK);

    @NonNull
    private final List<Color> colors;

    ColorScheme(Color... colors) {
        this.colors = Arrays.asList(colors);
    }

    public int getSize() {
        return colors.size();
    }

    @NonNull
    public Color getColor(int index) {
        return colors.get(index % colors.size());
    }

    @NonNull
    public List<Color> getColors() {
        return colors;
    }

    @NonNull
    public Color getRandomColor() {
        return colors.get((int) (Math.random() * colors.size()));
    }

    private static class Colors {
        private static final Color GOOGLE_RED = Color.valueOf(0xFFCC342B);
        private static final Color GOOGLE_ORANGE = Color.valueOf(0xFFF96A38);
        private static final Color GOOGLE_YELLOW = Color.valueOf(0xFFFBA922);
        private static final Color GOOGLE_GREEN = Color.valueOf(0xFF198844);
        private static final Color GOOGLE_DARK_BLUE = Color.valueOf(0xFF3971ED);
        private static final Color GOOGLE_LIGHT_BLUE = Color.valueOf(0xFF79A4F9);
        private static final Color GOOGLE_PURPLE = Color.valueOf(0xFFA36AC7);
        private static final Color GOOGLE_PINK = Color.valueOf(0xFFEC9998);
    }
}
