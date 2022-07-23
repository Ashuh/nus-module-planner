package com.ashuh.nusmoduleplanner.common.data.remote.model.module;

import static com.ashuh.nusmoduleplanner.testutil.ExampleLessonNos.LESSON_NO_1;
import static com.ashuh.nusmoduleplanner.testutil.ExampleLessonNos.LESSON_NO_2;
import static com.ashuh.nusmoduleplanner.testutil.ExampleLessonSizes.LESSON_SIZE_LEC;
import static com.ashuh.nusmoduleplanner.testutil.ExampleLessonSizes.LESSON_SIZE_TUT;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiLessons.API_LESSON_LEC_1_MON;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiLessons.API_LESSON_LEC_1_TUE;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiLessons.API_LESSON_TUT_1_WED;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiLessons.API_LESSON_TUT_2_THU;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleLessonOccurrences.LESSON_OCCURRENCE_MON;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleLessonOccurrences.LESSON_OCCURRENCE_THU;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleLessonOccurrences.LESSON_OCCURRENCE_TUE;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleLessonOccurrences.LESSON_OCCURRENCE_WED;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import org.junit.jupiter.api.Test;

import java.util.List;

class ApiLessonTest {
    @Test
    void toDomain() {
        Lesson expected;

        expected = new Lesson(LESSON_NO_1, LessonType.LECTURE, LESSON_SIZE_LEC,
                List.of(LESSON_OCCURRENCE_MON));
        assertEquals(API_LESSON_LEC_1_MON.toDomain(), expected);

        expected = new Lesson(LESSON_NO_1, LessonType.LECTURE, LESSON_SIZE_LEC,
                List.of(LESSON_OCCURRENCE_TUE));
        assertEquals(API_LESSON_LEC_1_TUE.toDomain(), expected);

        expected = new Lesson(LESSON_NO_1, LessonType.TUTORIAL, LESSON_SIZE_TUT,
                List.of(LESSON_OCCURRENCE_WED));
        assertEquals(API_LESSON_TUT_1_WED.toDomain(), expected);

        expected = new Lesson(LESSON_NO_2, LessonType.TUTORIAL, LESSON_SIZE_TUT,
                List.of(LESSON_OCCURRENCE_THU));
        assertEquals(API_LESSON_TUT_2_THU.toDomain(), expected);
    }
}
