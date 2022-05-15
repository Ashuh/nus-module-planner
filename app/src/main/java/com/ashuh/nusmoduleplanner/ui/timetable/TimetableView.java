package com.ashuh.nusmoduleplanner.ui.timetable;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.ashuh.nusmoduleplanner.MainActivity;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.Lesson;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.lesson.LessonType;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.model.timetable.TimetableEvent;
import com.ashuh.nusmoduleplanner.util.ColorScheme;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.jlurena.revolvingweekview.DateTimeInterpreter;
import me.jlurena.revolvingweekview.DayTime;
import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class TimetableView extends WeekView {

    private static final int HOUR_TIMETABLE_START = 8;
    private static final int HOUR_TIMETABLE_END = 22;
    private static final int HOUR_NOON = 12;

    private static final int TEXT_SIZE = 12;
    private static final int TEXT_SIZE_EVENT = 10;

    private SemesterType semType = null;
    private List<AssignedModule> assignedModules;

    public TimetableView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
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

        setOnEventClickListener(new TimetableClickListener(context));
        setDateTimeInterpreter(new TimetableDateTimeInterpreter());
        setWeekViewLoader(new TimetableLoader());
    }

    public TimetableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimetableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setSemType(SemesterType semType) {
        this.semType = semType;
    }

    public void setAssignedModules(List<AssignedModule> assignedModules) {
        this.assignedModules = assignedModules;
        notifyDatasetChanged();
    }

    private static class TimetableDateTimeInterpreter implements DateTimeInterpreter {

        @Override
        public String interpretDate(DayOfWeek day) {
            return day.getDisplayName(org.threeten.bp.format.TextStyle.SHORT, Locale.getDefault());
        }

        @Override
        public String interpretTime(int hour, int minutes) {
            String strMinutes = String.format(Locale.getDefault(), "%02d", minutes);

            if (hour >= HOUR_NOON) {
                return ((hour == HOUR_NOON) ? "12" : (hour - HOUR_NOON)) + ":" + strMinutes + " PM";
            } else {
                return ((hour == 0) ? "12:" : String.valueOf(hour)) + ":" + strMinutes + " AM";
            }
        }
    }

    private static class TimetableClickListener implements WeekView.EventClickListener {
        private final Context context;

        TimetableClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onEventClick(WeekViewEvent event, RectF eventRect) {
            TimetableEvent ttEvent = (TimetableEvent) event;

            if (ttEvent.getAlternateLessonCodes().isEmpty()) {
                return;
            }

            DialogFragment fragment = new LessonSelectDialogFragment(ttEvent);
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            fragment.show(fragmentManager, "lessons");
        }
    }

    private class TimetableLoader implements WeekViewLoader {
        @Override
        public List<? extends WeekViewEvent> onWeekViewLoad() {
            List<WeekViewEvent> events = new ArrayList<>();

            if (assignedModules == null) {
                return events;
            }

            for (AssignedModule assignedModule : assignedModules) {
                for (LessonType lessonType : assignedModule.getAssignedLessons().keySet()) {
                    String assignedLessonCode = assignedModule.getAssignedLessons()
                            .get(lessonType);

                    List<Lesson> lessons =
                            assignedModule.getTimetable(lessonType, assignedLessonCode);
                    int color = ColorScheme.GOOGLE.getRandomColor().toArgb();

                    for (Lesson lesson : lessons) {
                        DayTime startTime = lesson.getStartDayTime();
                        DayTime endTime = lesson.getEndDayTime();

                        TimetableEvent event = new TimetableEvent(assignedModule,
                                lesson, semType, startTime, endTime);

                        event.setLocation(lesson.getVenue());
                        event.setColor(color);
                        events.add(event);
                    }
                }
            }

            return events;
        }
    }
}
