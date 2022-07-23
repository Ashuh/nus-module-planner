package com.ashuh.nusmoduleplanner.testutil.data;

import static com.ashuh.nusmoduleplanner.testutil.ExampleLessonNos.LESSON_NO_1;
import static com.ashuh.nusmoduleplanner.testutil.ExampleLessonNos.LESSON_NO_2;
import static com.ashuh.nusmoduleplanner.testutil.ExampleLessonSizes.LESSON_SIZE_LEC;
import static com.ashuh.nusmoduleplanner.testutil.ExampleLessonSizes.LESSON_SIZE_TUT;
import static com.ashuh.nusmoduleplanner.testutil.ExampleVenues.VENUE_LECTURE;
import static com.ashuh.nusmoduleplanner.testutil.ExampleVenues.VENUE_TUTORIAL;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiWeeksResponse.API_WEEK_RESPONSE_LIST_ALL_WEEKS;

import com.ashuh.nusmoduleplanner.common.data.remote.model.module.ApiLesson;

public class ExampleApiLessons {
    public static final String LESSON_TYPE_LEC = "Lecture";
    public static final String LESSON_TYPE_TUT = "Tutorial";

    private static final String DAY_MONDAY = "Monday";
    private static final String DAY_TUESDAY = "Tuesday";
    private static final String DAY_WEDNESDAY = "Wednesday";
    private static final String DAY_THURSDAY = "Thursday";

    private static final String TIME_1000 = "1000";
    private static final String TIME_1200 = "1200";

    public static final ApiLesson API_LESSON_LEC_1_MON
            = new ApiLesson(LESSON_NO_1, TIME_1000, TIME_1200, API_WEEK_RESPONSE_LIST_ALL_WEEKS,
            VENUE_LECTURE, DAY_MONDAY, LESSON_TYPE_LEC, LESSON_SIZE_LEC);

    public static final ApiLesson API_LESSON_LEC_1_TUE
            = new ApiLesson(LESSON_NO_1, TIME_1000, TIME_1200, API_WEEK_RESPONSE_LIST_ALL_WEEKS,
            VENUE_LECTURE, DAY_TUESDAY, LESSON_TYPE_LEC, LESSON_SIZE_LEC);

    public static final ApiLesson API_LESSON_TUT_1_WED
            = new ApiLesson(LESSON_NO_1, TIME_1000, TIME_1200, API_WEEK_RESPONSE_LIST_ALL_WEEKS,
            VENUE_TUTORIAL, DAY_WEDNESDAY, LESSON_TYPE_TUT, LESSON_SIZE_TUT);

    public static final ApiLesson API_LESSON_TUT_2_THU
            = new ApiLesson(LESSON_NO_2, TIME_1000, TIME_1200, API_WEEK_RESPONSE_LIST_ALL_WEEKS,
            VENUE_TUTORIAL, DAY_THURSDAY, LESSON_TYPE_TUT, LESSON_SIZE_TUT);
}
