package com.ashuh.nusmoduleplanner.common.util;


import android.graphics.Color;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public enum ColorScheme {
    ASHES(Color.valueOf(0XFFC7AE95),
            Color.valueOf(0XFFC7C795),
            Color.valueOf(0XFFAEC795),
            Color.valueOf(0XFF95C7AE),
            Color.valueOf(0XFF95AEC7),
            Color.valueOf(0XFFAE95C7),
            Color.valueOf(0XFFC795AE),
            Color.valueOf(0XFFC79595)),

    CHALK(Color.valueOf(0XFFFB9FB1),
            Color.valueOf(0XFFEDA987),
            Color.valueOf(0XFFDDB26F),
            Color.valueOf(0XFFACC267),
            Color.valueOf(0XFF12CFC0),
            Color.valueOf(0XFF6FC2EF),
            Color.valueOf(0XFFE1A3EE),
            Color.valueOf(0XFFDEAF8F)),

    EIGHTIES(Color.valueOf(0XFFF2777A),
            Color.valueOf(0XFFF99157),
            Color.valueOf(0XFFFFCC66),
            Color.valueOf(0XFF99CC99),
            Color.valueOf(0XFF66CCCC),
            Color.valueOf(0XFF6699CC),
            Color.valueOf(0XFFCC99CC),
            Color.valueOf(0XFFD27B53)),

    GOOGLE(Color.valueOf(0XFFCC342B),
            Color.valueOf(0XFFF96A38),
            Color.valueOf(0XFFFBA922),
            Color.valueOf(0XFF198844),
            Color.valueOf(0XFF3971ED),
            Color.valueOf(0XFF79A4F9),
            Color.valueOf(0XFFA36AC7),
            Color.valueOf(0XFFEC9998)),

    MOCHA(Color.valueOf(0XFFCB6077),
            Color.valueOf(0XFFD28B71),
            Color.valueOf(0XFFF4BC87),
            Color.valueOf(0XFFBEB55B),
            Color.valueOf(0XFF7BBDA4),
            Color.valueOf(0XFF8AB3B5),
            Color.valueOf(0XFFA89BB9),
            Color.valueOf(0XFFBB9584)),

    MONOKAI(Color.valueOf(0XFFF92672),
            Color.valueOf(0XFFF9931A),
            Color.valueOf(0XFFF4BF75),
            Color.valueOf(0XFFA6E22E),
            Color.valueOf(0XFFA1EFE4),
            Color.valueOf(0XFF66D9EF),
            Color.valueOf(0XFFAE81FF),
            Color.valueOf(0XFFCC6633)),

    OCEAN(Color.valueOf(0XFFBF616A),
            Color.valueOf(0XFFD08770),
            Color.valueOf(0XFFEBCB8B),
            Color.valueOf(0XFFA3BE8C),
            Color.valueOf(0XFF96B5B4),
            Color.valueOf(0XFF8FA1B3),
            Color.valueOf(0XFFB48EAD),
            Color.valueOf(0XFFAB7967)),

    OCEANIC_NEXT(Color.valueOf(0XFFEC5F67),
            Color.valueOf(0XFFF99157),
            Color.valueOf(0XFFFAC863),
            Color.valueOf(0XFF99C794),
            Color.valueOf(0XFF5FB3B3),
            Color.valueOf(0XFF6699CC),
            Color.valueOf(0XFFC594C5),
            Color.valueOf(0XFFAB7967)),


    PARAISO(Color.valueOf(0XFFEF6155),
            Color.valueOf(0XFFF99B15),
            Color.valueOf(0XFFFEC418),
            Color.valueOf(0XFF48B685),
            Color.valueOf(0XFF5BC4BF),
            Color.valueOf(0XFF06B6EF),
            Color.valueOf(0XFF815BA4),
            Color.valueOf(0XFFE96BA8)),

    RAILSCASTS(Color.valueOf(0XFFDA4939),
            Color.valueOf(0XFFCC7833),
            Color.valueOf(0XFFECC56A),
            Color.valueOf(0XFFA5C261),
            Color.valueOf(0XFF519F50),
            Color.valueOf(0XFF6D9CBE),
            Color.valueOf(0XFFB6B3EB),
            Color.valueOf(0XFFBC9458)),

    TOMORROW(Color.valueOf(0XFFCC6666),
            Color.valueOf(0XFFDE935F),
            Color.valueOf(0XFFF0C674),
            Color.valueOf(0XFFB5BD68),
            Color.valueOf(0XFF8ABEB7),
            Color.valueOf(0XFF81A2BE),
            Color.valueOf(0XFFB294BB),
            Color.valueOf(0XFFA3685A)),

    TWILIGHT(Color.valueOf(0XFFCF6A4C),
            Color.valueOf(0XFFCDA869),
            Color.valueOf(0XFFF9EE98),
            Color.valueOf(0XFF8F9D6A),
            Color.valueOf(0XFFAFC4DB),
            Color.valueOf(0XFF7587A6),
            Color.valueOf(0XFF9B859D),
            Color.valueOf(0XFF9B703F));

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
        StringJoiner joiner = new StringJoiner(" ");
        for (String word : name().split("_")) {
            joiner.add(word.charAt(0) + word.substring(1).toLowerCase());
        }
        return joiner.toString();
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
