package com.ashuh.nusmoduleplanner.timetable.presentation.model;

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
        this.lessonType = lessonType;
        this.lessonNo = lessonNo;
        this.occurrences = occurrences;
        this.onClick = onClick;
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
