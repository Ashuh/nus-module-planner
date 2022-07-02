package com.ashuh.nusmoduleplanner.timetable.presentation.model;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.util.List;

public class UiLesson {
    @NonNull
    private final String lessonType;
    @NonNull
    private final String lessonNo;
    @NonNull
    private final List<UiLessonOccurrence> occurrences;
    @NonNull
    private final Runnable onClick;

    public UiLesson(@NonNull String lessonType, @NonNull String lessonNo,
                    @NonNull List<UiLessonOccurrence> occurrences, @NonNull Runnable onClick) {
        this.lessonType = requireNonNull(lessonType);
        this.lessonNo = requireNonNull(lessonNo);
        this.occurrences = requireNonNull(occurrences);
        this.onClick = requireNonNull(onClick);
    }

    @NonNull
    public String getLessonType() {
        return lessonType;
    }

    @NonNull
    public String getLessonNo() {
        return lessonNo;
    }

    @NonNull
    public List<UiLessonOccurrence> getOccurrences() {
        return occurrences;
    }

    public void onClick() {
        onClick.run();
    }
}
