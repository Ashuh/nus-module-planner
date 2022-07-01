package com.ashuh.nusmoduleplanner.common.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final String DATE_FORMAT_DISPLAY = "dd-MMM-yyyy h:mm a";
    private static final DateTimeFormatter DATE_FORMATTER_DISPLAY
            = DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_DISPLAY);

    public static String formatZonedDateTimeForDisplay(ZonedDateTime dateTime) {
        return dateTime.withZoneSameInstant(ZoneId.systemDefault()).format(DATE_FORMATTER_DISPLAY);
    }
}
