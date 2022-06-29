package com.ashuh.nusmoduleplanner.timetable.presentation;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.timetable.presentation.model.TimetableEvent;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiModuleReading;

import java.util.List;

public class TimetableState {
    @NonNull
    private final List<TimetableEvent> timetableEvents;
    @NonNull
    private final List<UiModuleReading> moduleReadings;

    public TimetableState(@NonNull List<TimetableEvent> timetableEvents,
                          @NonNull List<UiModuleReading> moduleReadings) {
        requireNonNull(timetableEvents);
        requireNonNull(moduleReadings);
        this.timetableEvents = timetableEvents;
        this.moduleReadings = moduleReadings;
    }

    @NonNull
    public List<TimetableEvent> getTimetableEvents() {
        return timetableEvents;
    }

    @NonNull
    public List<UiModuleReading> getModuleReadings() {
        return moduleReadings;
    }
}
