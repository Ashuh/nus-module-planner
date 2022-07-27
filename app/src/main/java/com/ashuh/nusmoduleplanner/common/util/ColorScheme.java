package com.ashuh.nusmoduleplanner.common.util;


import android.graphics.Color;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public enum ColorScheme {
    EIGHTIES(Color.valueOf(0xFFF2777A),
            Color.valueOf(0xFFf99157),
            Color.valueOf(0xFFFFCC66),
            Color.valueOf(0xFF99CC99),
            Color.valueOf(0xFF66CCCC),
            Color.valueOf(0xFF6699CC),
            Color.valueOf(0xFFCC99CC),
            Color.valueOf(0xFFD27B53)),

    GOOGLE(Color.valueOf(0xFFCC342B),
            Color.valueOf(0xFFF96A38),
            Color.valueOf(0xFFFBA922),
            Color.valueOf(0xFF198844),
            Color.valueOf(0xFF3971ED),
            Color.valueOf(0xFF79A4F9),
            Color.valueOf(0xFFA36AC7),
            Color.valueOf(0xFFEC9998));

    private static final int SIZE = 8;

    @NonNull
    private final List<Color> colors;

    ColorScheme(Color... colors) {
        if (colors.length != SIZE) {
            throw new IllegalArgumentException("ColorScheme must have exactly " + SIZE + " colors");
        }
        this.colors = Arrays.asList(colors);
    }

    public int getSize() {
        return colors.size();
    }

    @NonNull
    public Color getColor(@NonNull Index index) {
        return colors.get(index.getValue());
    }

    @NonNull
    public List<Color> getColors() {
        return colors;
    }

    @NonNull
    public Color getRandomColor() {
        return colors.get((int) (Math.random() * colors.size()));
    }

    @NonNull
    @Override
    public String toString() {
        String name = name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }

    public static class Index {
        private final byte value;

        public Index(byte value) {
            if (value < 0 || value >= SIZE) {
                throw new IllegalArgumentException("Index must be between 0 and " + (SIZE - 1));
            }
            this.value = value;
        }

        public static Index random() {
            return new Index((byte) (Math.random() * SIZE));
        }

        public byte getValue() {
            return value;
        }
    }
}
