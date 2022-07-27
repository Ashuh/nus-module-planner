package com.ashuh.nusmoduleplanner.timetable.presentation;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiTimetableLessonOccurrence;

import java.util.ArrayList;
import java.util.List;

import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class TimetableLoader implements WeekView.WeekViewLoader {
    @NonNull
    private final List<UiTimetableLessonOccurrence> lessonOccurrences = new ArrayList<>();

    @Override
    public List<? extends WeekViewEvent> onWeekViewLoad() {
        return lessonOccurrences;
    }

    public void setOccurrences(@NonNull List<UiTimetableLessonOccurrence> lessonOccurrences) {
        this.lessonOccurrences.clear();
        this.lessonOccurrences.addAll(lessonOccurrences);
    }
}
