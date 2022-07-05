package com.ashuh.nusmoduleplanner.timetable.presentation;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiModuleReading;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiTimetableLessonOccurrence;

import java.util.Collections;
import java.util.List;

public class TimetableState {
    @NonNull
    private final List<UiTimetableLessonOccurrence> lessonOccurrences;
    @NonNull
    private final List<UiModuleReading> moduleReadings;
    private final boolean isLoading;

    public TimetableState(@NonNull List<UiTimetableLessonOccurrence> lessonOccurrences,
                          @NonNull List<UiModuleReading> moduleReadings, boolean isLoading) {
        this.lessonOccurrences = requireNonNull(lessonOccurrences);
        this.moduleReadings = requireNonNull(moduleReadings);
        this.isLoading = isLoading;
    }

    public static TimetableState loading() {
        return new TimetableState(Collections.emptyList(), Collections.emptyList(), true);
    }

    public boolean isLoading() {
        return isLoading;
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
