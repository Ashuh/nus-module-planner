package com.ashuh.nusmoduleplanner.data.model.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DateUtil {

    public static final String DATE_FORMAT_API = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_FORMAT_DISPLAY = "yyyy-MM-dd h:mm a";
    public static final String DATE_FORMAT_DISPLAY_SHORT = "MMM d";

    public static final DateTimeFormatter DATE_FORMATTER_API =
            DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_API);
    public static final DateTimeFormatter DATE_FORMATTER_DISPLAY =
            DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_DISPLAY);
    public static final DateTimeFormatter DATE_FORMATTER_DISPLAY_SHORT =
            DateTimeFormatter.ofPattern(DATE_FORMAT_DISPLAY_SHORT);

}
