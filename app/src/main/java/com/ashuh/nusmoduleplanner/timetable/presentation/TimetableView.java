package com.ashuh.nusmoduleplanner.timetable.presentation;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;

import org.threeten.bp.DayOfWeek;

import java.util.Locale;

import me.jlurena.revolvingweekview.DateTimeInterpreter;
import me.jlurena.revolvingweekview.WeekView;

public class TimetableView extends WeekView {
    private static final int HOUR_TIMETABLE_START = 8;
    private static final int HOUR_TIMETABLE_END = 22;
    private static final int HOUR_NOON = 12;

    private static final int TEXT_SIZE = 12;
    private static final int TEXT_SIZE_EVENT = 10;

    public TimetableView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setHorizontalFlingEnabled(false);
        goToDate(DayOfWeek.MONDAY);
        setMinTime(HOUR_TIMETABLE_START);
        setMaxTime(HOUR_TIMETABLE_END);

        setOverlappingEventGap(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                        getResources().getDisplayMetrics()));
        setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE,
                getResources().getDisplayMetrics()));
        setEventTextSize(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_EVENT,
                        getResources().getDisplayMetrics()));

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
