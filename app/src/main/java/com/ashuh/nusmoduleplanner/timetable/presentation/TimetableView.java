package com.ashuh.nusmoduleplanner.timetable.presentation;

import android.content.Context;
import android.util.AttributeSet;

import org.threeten.bp.DayOfWeek;

import java.util.Locale;

import me.jlurena.revolvingweekview.DateTimeInterpreter;
import me.jlurena.revolvingweekview.WeekView;

public class TimetableView extends WeekView {
    private static final int HOUR_NOON = 12;

    public TimetableView(Context context) {
        super(context);
        init();
    }

    private void init() {
        goToDate(DayOfWeek.MONDAY);
        setDateTimeInterpreter(new TimetableDateTimeInterpreter());
    }

    public TimetableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimetableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private static class TimetableDateTimeInterpreter implements DateTimeInterpreter {

        private static final String TEXT_TIME_FORMAT = "%d:%02d %s";

        @Override
        public String interpretDate(DayOfWeek day) {
            return day.getDisplayName(org.threeten.bp.format.TextStyle.SHORT, Locale.getDefault());
        }

        @Override
        public String interpretTime(int hour, int minutes) {
            int hourConverted = (hour % HOUR_NOON == 0) ? HOUR_NOON : hour % HOUR_NOON;
            return String.format(Locale.ENGLISH, TEXT_TIME_FORMAT, hourConverted, minutes,
                    hour < HOUR_NOON ? "AM" : "PM");
        }
    }
}
