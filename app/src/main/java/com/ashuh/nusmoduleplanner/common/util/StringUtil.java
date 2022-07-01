package com.ashuh.nusmoduleplanner.common.util;

import java.text.DecimalFormat;

public class StringUtil {
    private static final String FORMAT_NO_TRAILING_ZEROS = "0.#";
    private static final DecimalFormat FORMATTER_NO_TRAILING_ZEROS
            = new DecimalFormat(FORMAT_NO_TRAILING_ZEROS);

    public static String doubleWithoutTrailingZeros(double value) {
        return FORMATTER_NO_TRAILING_ZEROS.format(value);
    }
}
