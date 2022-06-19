package com.ashuh.nusmoduleplanner.common.data.local.model.module.lessonoccurrence;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks.WeeksAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks.WeeksEntity;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Weeks;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonOccurrence;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class LessonOccurrenceAggregate {
    @NonNull
    @Embedded
    private final LessonOccurrenceEntity lessonOccurrence;
    @NonNull
    @Relation(
            parentColumn = "id",
            entityColumn = "ownerId",
            entity = WeeksEntity.class
    )
    private final WeeksAggregate weeksAggregate;

    public LessonOccurrenceAggregate(@NonNull LessonOccurrenceEntity lessonOccurrence,
                                     @NonNull WeeksAggregate weeksAggregate) {
        requireNonNull(lessonOccurrence);
        requireNonNull(weeksAggregate);
        this.lessonOccurrence = lessonOccurrence;
        this.weeksAggregate = weeksAggregate;
    }

    @NonNull
    public static LessonOccurrenceAggregate fromDomain(@NonNull LessonOccurrence lessonOccurrence) {
        DayOfWeek day = lessonOccurrence.getDay();
        String startTime = lessonOccurrence.getStartTime().toString();
        String endTime = lessonOccurrence.getEndTime().toString();
        String venue = lessonOccurrence.getVenue();
        LessonOccurrenceEntity lessonOccurrenceEntity
                = new LessonOccurrenceEntity(day, startTime, endTime, venue);
        WeeksAggregate weeks = WeeksAggregate.fromDomain(lessonOccurrence.getWeeks());
        return new LessonOccurrenceAggregate(lessonOccurrenceEntity, weeks);
    }

    @NonNull
    public LessonOccurrence toDomain() {
        DayOfWeek domainDay = lessonOccurrence.getDay();
        LocalTime domainStartTime = LocalTime.parse(lessonOccurrence.getStartTime());
        LocalTime domainEndTime = LocalTime.parse(lessonOccurrence.getEndTime());
        Weeks domainWeeks = weeksAggregate.toDomain();
        String domainVenue = lessonOccurrence.getVenue();
        return new LessonOccurrence(domainDay, domainStartTime, domainEndTime, domainWeeks,
                domainVenue);
    }

    @NonNull
    public LessonOccurrenceEntity getLessonOccurrence() {
        return lessonOccurrence;
    }

    @NonNull
    public WeeksAggregate getWeeksAggregate() {
        return weeksAggregate;
    }
}
