package com.ashuh.nusmoduleplanner.timetable.presentation;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiLessonOccurrence;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiModuleReading;

import java.util.List;

public class TimetableState {
    @NonNull
    private final List<UiLessonOccurrence> lessonOccurrences;
    @NonNull
    private final List<UiModuleReading> moduleReadings;

    public TimetableState(@NonNull List<UiLessonOccurrence> lessonOccurrences,
                          @NonNull List<UiModuleReading> moduleReadings) {
        requireNonNull(lessonOccurrences);
        requireNonNull(moduleReadings);
        this.lessonOccurrences = lessonOccurrences;
        this.moduleReadings = moduleReadings;
    }

    @NonNull
    public List<UiLessonOccurrence> getLessonOccurrences() {
        return lessonOccurrences;
    }

    @NonNull
    public List<UiModuleReading> getModuleReadings() {
        return moduleReadings;
    }
}
