package com.ashuh.nusmoduleplanner.common.domain.model.module.lesson;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Lesson implements Comparable<Lesson> {
    @NonNull
    private final String lessonNo;
    @NonNull
    private final LessonType lessonType;
    private final int size;
    @NonNull
    private final List<LessonOccurrence> occurrences;

    public Lesson(@NonNull String lessonNo, @NonNull LessonType lessonType, int size,
                  @NonNull LessonOccurrence occurrence) {
        this(lessonNo, lessonType, size, new ArrayList<>());
        requireNonNull(occurrence);
        occurrences.add(occurrence);
    }

    public Lesson(@NonNull String lessonNo, @NonNull LessonType lessonType, int size,
                  @NonNull List<LessonOccurrence> occurrences) {
        this.lessonNo = requireNonNull(lessonNo);
        this.lessonType = requireNonNull(lessonType);
        this.size = size;
        this.occurrences = requireNonNull(occurrences);
    }

    @NonNull
    public String getLessonNo() {
        return lessonNo;
    }

    @NonNull
    public LessonType getLessonType() {
        return lessonType;
    }

    public int getSize() {
        return size;
    }

    @NonNull
    public List<LessonOccurrence> getOccurrences() {
        return occurrences;
    }

    public void addOccurrences(@NonNull Collection<LessonOccurrence> occurrence) {
        requireNonNull(occurrence);
        occurrences.addAll(occurrence);
    }

    @Override
    public int compareTo(Lesson lesson) {
        return  lessonNo.compareTo(lesson.lessonNo);
    }
}
