package com.ashuh.nusmoduleplanner.testutil.domain;

import static com.ashuh.nusmoduleplanner.testutil.ExampleVenues.VENUE_LECTURE;
import static com.ashuh.nusmoduleplanner.testutil.ExampleVenues.VENUE_TUTORIAL;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleWeeks.WEEKS_ALL;

import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonOccurrence;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ExampleLessonOccurrences {
    private static final LocalTime TIME_1000 = LocalTime.of(10, 0);
    private static final LocalTime TIME_1200 = LocalTime.of(12, 0);

    public static final LessonOccurrence LESSON_OCCURRENCE_MON
            = new LessonOccurrence(DayOfWeek.MONDAY, TIME_1000, TIME_1200, WEEKS_ALL,
            VENUE_LECTURE);

    public static final LessonOccurrence LESSON_OCCURRENCE_TUE
            = new LessonOccurrence(DayOfWeek.TUESDAY, TIME_1000, TIME_1200, WEEKS_ALL,
            VENUE_LECTURE);

    public static final LessonOccurrence LESSON_OCCURRENCE_WED
            = new LessonOccurrence(DayOfWeek.WEDNESDAY, TIME_1000, TIME_1200, WEEKS_ALL,
            VENUE_TUTORIAL);

    public static final LessonOccurrence LESSON_OCCURRENCE_THU
            = new LessonOccurrence(DayOfWeek.THURSDAY, TIME_1000, TIME_1200, WEEKS_ALL,
            VENUE_TUTORIAL);
}
