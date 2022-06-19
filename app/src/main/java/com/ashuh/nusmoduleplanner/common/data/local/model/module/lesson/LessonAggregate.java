package com.ashuh.nusmoduleplanner.common.data.local.model.module.lesson;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.lessonoccurrence.LessonOccurrenceAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.lessonoccurrence.LessonOccurrenceEntity;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonOccurrence;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

import java.util.List;
import java.util.stream.Collectors;

public class LessonAggregate {
    @NonNull
    @Embedded
    private final LessonEntity lesson;
    @NonNull
    @Relation(
            parentColumn = "id",
            entityColumn = "ownerId",
            entity = LessonOccurrenceEntity.class
    )
    private final List<LessonOccurrenceAggregate> lessonOccurrenceAggregates;

    public LessonAggregate(@NonNull LessonEntity lesson,
                           @NonNull List<LessonOccurrenceAggregate> lessonOccurrenceAggregates) {
        requireNonNull(lesson);
        requireNonNull(lessonOccurrenceAggregates);
        this.lesson = lesson;
        this.lessonOccurrenceAggregates = lessonOccurrenceAggregates;
    }

    @NonNull
    public static LessonAggregate fromDomain(@NonNull Lesson lesson) {
        String lessonType = lesson.getLessonType().name();
        String classNo = lesson.getLessonNo();
        int size = lesson.getSize();
        LessonEntity lessonEntity = new LessonEntity(lessonType, classNo, size);

        List<LessonOccurrenceAggregate> occurrences = lesson.getOccurrences().stream()
                .map(LessonOccurrenceAggregate::fromDomain)
                .collect(Collectors.toList());
        return new LessonAggregate(lessonEntity, occurrences);
    }

    @NonNull
    public Lesson toDomain() {
        String classNo = lesson.getClassNo();
        LessonType lessonType = LessonType.valueOf(lesson.getLessonType());
        int size = lesson.getSize();
        List<LessonOccurrence> lessonOccurrences = lessonOccurrenceAggregates.stream()
                .map(LessonOccurrenceAggregate::toDomain)
                .collect(Collectors.toList());
        return new Lesson(classNo, lessonType, size, lessonOccurrences);
    }

    @NonNull
    public LessonEntity getLesson() {
        return lesson;
    }

    @NonNull
    public List<LessonOccurrenceAggregate> getLessonOccurrenceAggregates() {
        return lessonOccurrenceAggregates;
    }

    @NonNull
    @Override
    public String toString() {
        return "LessonAggregate{"
                + "lesson=" + lesson
                + ", lessonOccurrenceAggregates=" + lessonOccurrenceAggregates
                + '}';
    }
}
