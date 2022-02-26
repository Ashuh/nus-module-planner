package com.ashuh.nusmoduleplanner.ui.timetable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.ashuh.nusmoduleplanner.MainActivity;
import com.ashuh.nusmoduleplanner.data.model.nusmods.Lesson;
import com.ashuh.nusmoduleplanner.data.model.nusmods.Semester;
import com.ashuh.nusmoduleplanner.data.model.nusmods.SemesterDetail;
import com.ashuh.nusmoduleplanner.data.model.nusmods.Timetable;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.model.timetable.TimetableEvent;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import me.jlurena.revolvingweekview.DateTimeInterpreter;
import me.jlurena.revolvingweekview.DayTime;
import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class TimetableView extends WeekView {
    private Semester semType = null;
    private List<AssignedModule> assignedModules;

    public TimetableView(Context context) {
        super(context);
        init(context);
    }

    public TimetableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimetableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setSemType(Semester semType) {
        this.semType = semType;
    }

    public void setAssignedModules(List<AssignedModule> assignedModules) {
        this.assignedModules = assignedModules;
        notifyDatasetChanged();
    }

    private void init(Context context) {
        setHorizontalFlingEnabled(false);
        goToDate(DayOfWeek.MONDAY);
        setMinTime(8);
        setMaxTime(22);

        setOverlappingEventGap(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                        getResources().getDisplayMetrics()));
        setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                getResources().getDisplayMetrics()));
        setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
                getResources().getDisplayMetrics()));

        setOnEventClickListener(new TimetableClickListener(context));
        setDateTimeInterpreter(new TimetableDateTimeInterpreter());
        setWeekViewLoader(new TimetableLoader());
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private static class TimetableDateTimeInterpreter implements DateTimeInterpreter {

        @Override
        public String interpretDate(DayOfWeek day) {
            return day.getDisplayName(org.threeten.bp.format.TextStyle.SHORT, Locale.getDefault());
        }

        @Override
        public String interpretTime(int hour, int minutes) {
            String strMinutes = String.format(Locale.getDefault(), "%02d", minutes);

            if (hour > 11) {
                return ((hour == 12) ? "12" : (hour - 12)) + ":" + strMinutes + " PM";
            } else {
                return ((hour == 0) ? "12:" : String.valueOf(hour)) + ":" + strMinutes + " AM";
            }
        }
    }

    private static class TimetableClickListener implements WeekView.EventClickListener {
        private final Context context;

        public TimetableClickListener(Context context) {
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
                SemesterDetail semData =
                        assignedModule.getModuleDetail().getSemesterDetail(semType);
                Timetable timetable = new Timetable(semData.getLessons(),
                        assignedModule.getModuleDetail().getModuleCode(), semType);

                for (Lesson.Type lessonType : timetable.getLessonTypes()) {
                    String assignedLessonCode = assignedModule.getAssignedLessons()
                            .get(lessonType);

                    List<Lesson> lessons = timetable.getLessons(lessonType, assignedLessonCode);
                    int color = getRandomColor();

                    for (Lesson lesson : lessons) {
                        DayTime startTime = lesson.getStartDayTime();
                        DayTime endTime = lesson.getEndDayTime();

                        TimetableEvent event = new TimetableEvent(timetable,
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
