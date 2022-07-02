package com.ashuh.nusmoduleplanner.timetable.presentation;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiTimetableLessonOccurrence;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiModuleReading;

import java.util.List;

public class TimetableState {
    @NonNull
    private final List<UiTimetableLessonOccurrence> lessonOccurrences;
    @NonNull
    private final List<UiModuleReading> moduleReadings;

    public TimetableState(@NonNull List<UiTimetableLessonOccurrence> lessonOccurrences,
                          @NonNull List<UiModuleReading> moduleReadings) {
        this.lessonOccurrences = requireNonNull(lessonOccurrences);
        this.moduleReadings = requireNonNull(moduleReadings);
    }

    @NonNull
    public List<UiTimetableLessonOccurrence> getLessonOccurrences() {
        return lessonOccurrences;
    }

    @NonNull
    public List<UiModuleReading> getModuleReadings() {
        return moduleReadings;
    }
}
