package com.ashuh.nusmoduleplanner.settings;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiTimetableLessonOccurrence;

import org.threeten.bp.DayOfWeek;

import java.util.List;

import me.jlurena.revolvingweekview.DayTime;
import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class TimetableSampleDataLoader implements WeekView.WeekViewLoader {
    private static final UiTimetableLessonOccurrence SAMPLE_COLOR_0_1
            = UiTimetableLessonOccurrence.create(
            "COM1-B113",
            new DayTime(DayOfWeek.TUESDAY, 10, 0),
            new DayTime(DayOfWeek.TUESDAY, 11, 0),
            "CS3235",
            LessonType.TUTORIAL,
            "2", 0);

    private static final UiTimetableLessonOccurrence SAMPLE_COLOR_0_2
            = UiTimetableLessonOccurrence.create(
            "COM1-B113",
            new DayTime(DayOfWeek.TUESDAY, 14, 0),
            new DayTime(DayOfWeek.TUESDAY, 16, 0),
            "CS3235",
            LessonType.LECTURE,
            "1", 0);

    private static final UiTimetableLessonOccurrence SAMPLE_COLOR_2
            = UiTimetableLessonOccurrence.create(
            "AS1-0207",
            new DayTime(DayOfWeek.WEDNESDAY, 10, 0),
            new DayTime(DayOfWeek.WEDNESDAY, 12, 0),
            "GER1000",
            LessonType.TUTORIAL,
            "A19", 0);

    private static final UiTimetableLessonOccurrence SAMPLE_COLOR_4
            = UiTimetableLessonOccurrence.create(
            "i3-Aud",
            new DayTime(DayOfWeek.WEDNESDAY, 12, 0),
            new DayTime(DayOfWeek.WEDNESDAY, 14, 0),
            "CS2100",
            LessonType.LECTURE,
            "1", 0);

    private static final UiTimetableLessonOccurrence SAMPLE_COLOR_6
            = UiTimetableLessonOccurrence.create(
            "BIZ2-0509",
            new DayTime(DayOfWeek.MONDAY, 10, 0),
            new DayTime(DayOfWeek.MONDAY, 13, 0),
            "ACC1006",
            LessonType.SECTIONAL,
            "1", 0);

    private static final UiTimetableLessonOccurrence SAMPLE_COLOR_7_1
            = UiTimetableLessonOccurrence.create(
            "i3-Aud",
            new DayTime(DayOfWeek.MONDAY, 14, 0),
            new DayTime(DayOfWeek.MONDAY, 16, 0),
            "CS2108",
            LessonType.LECTURE,
            "1", 0);

    private static final UiTimetableLessonOccurrence SAMPLE_COLOR_7_2
            = UiTimetableLessonOccurrence.create(
            "COM1-0208",
            new DayTime(DayOfWeek.TUESDAY, 11, 0),
            new DayTime(DayOfWeek.TUESDAY, 12, 0),
            "CS2108",
            LessonType.TUTORIAL,
            "2", 0);

    @Override
    public List<? extends WeekViewEvent> onWeekViewLoad() {
        return List.of(SAMPLE_COLOR_0_1, SAMPLE_COLOR_0_2, SAMPLE_COLOR_2, SAMPLE_COLOR_4,
                SAMPLE_COLOR_6, SAMPLE_COLOR_7_1, SAMPLE_COLOR_7_2);
    }

    public void setColorScheme(@NonNull ColorScheme colorScheme) {
        int color0 = colorScheme.getColor(new ColorScheme.Index(0)).toArgb();
        int color2 = colorScheme.getColor(new ColorScheme.Index(2)).toArgb();
        int color4 = colorScheme.getColor(new ColorScheme.Index(4)).toArgb();
        int color6 = colorScheme.getColor(new ColorScheme.Index(6)).toArgb();
        int color7 = colorScheme.getColor(new ColorScheme.Index(7)).toArgb();

        SAMPLE_COLOR_0_1.setColor(color0);
        SAMPLE_COLOR_0_2.setColor(color0);
        SAMPLE_COLOR_2.setColor(color2);
        SAMPLE_COLOR_4.setColor(color4);
        SAMPLE_COLOR_6.setColor(color6);
        SAMPLE_COLOR_7_1.setColor(color7);
        SAMPLE_COLOR_7_2.setColor(color7);
    }
}
