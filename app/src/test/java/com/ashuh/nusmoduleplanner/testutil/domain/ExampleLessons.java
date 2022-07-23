package com.ashuh.nusmoduleplanner.testutil.domain;

import static com.ashuh.nusmoduleplanner.testutil.ExampleLessonNos.LESSON_NO_1;
import static com.ashuh.nusmoduleplanner.testutil.ExampleLessonNos.LESSON_NO_2;
import static com.ashuh.nusmoduleplanner.testutil.ExampleLessonSizes.LESSON_SIZE_LEC;
import static com.ashuh.nusmoduleplanner.testutil.ExampleLessonSizes.LESSON_SIZE_TUT;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleLessonOccurrences.LESSON_OCCURRENCE_MON;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleLessonOccurrences.LESSON_OCCURRENCE_THU;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleLessonOccurrences.LESSON_OCCURRENCE_TUE;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleLessonOccurrences.LESSON_OCCURRENCE_WED;

import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import java.util.List;

public class ExampleLessons {
//    public static final Lesson LESSON_LEC_1_MON
//            = new Lesson(LESSON_NO_1, LessonType.LECTURE, LESSON_SIZE_LEC,
//            List.of(LESSON_OCCURRENCE_MON)
//    );
//
//    public static final Lesson LESSON_LEC_1_TUE
//            = new Lesson(LESSON_NO_1, LessonType.LECTURE, LESSON_SIZE_LEC,
//            List.of(LESSON_OCCURRENCE_TUE)
//    );

    public static final Lesson LESSON_LEC_1
            = new Lesson(LESSON_NO_1, LessonType.LECTURE, LESSON_SIZE_LEC,
            List.of(LESSON_OCCURRENCE_MON, LESSON_OCCURRENCE_TUE)
    );

    public static final Lesson LESSON_TUT_1
            = new Lesson(LESSON_NO_1, LessonType.TUTORIAL, LESSON_SIZE_TUT,
            List.of(LESSON_OCCURRENCE_WED));

    public static final Lesson LESSON_TUT_2
            = new Lesson(LESSON_NO_2, LessonType.TUTORIAL, LESSON_SIZE_TUT,
            List.of( LESSON_OCCURRENCE_THU));
}
