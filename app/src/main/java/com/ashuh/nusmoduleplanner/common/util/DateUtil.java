package com.ashuh.nusmoduleplanner.common.util;

import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String DATE_FORMAT_DISPLAY = "yyyy-MM-dd h:mm a";
    public static final String DATE_FORMAT_DISPLAY_SHORT = "MMM d";

    public static final DateTimeFormatter DATE_FORMATTER_DISPLAY
            = DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_DISPLAY);
    public static final DateTimeFormatter DATE_FORMATTER_DISPLAY_SHORT
            = DateTimeFormatter.ofPattern(DATE_FORMAT_DISPLAY_SHORT);
}
