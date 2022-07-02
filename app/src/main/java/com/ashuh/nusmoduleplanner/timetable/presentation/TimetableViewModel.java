package com.ashuh.nusmoduleplanner.timetable.presentation;

import static java.util.Objects.requireNonNull;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Exam;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Weeks;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonOccurrence;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;
import com.ashuh.nusmoduleplanner.common.util.DateUtil;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.DeleteModuleReadingUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.GetAlternateLessonsUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.GetModuleReadingsUseCase;
import com.ashuh.nusmoduleplanner.timetable.domain.usecase.UpdateLessonNoUseCase;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiLesson;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiLessonOccurrence;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiModuleReading;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiTimetableLessonOccurrence;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import me.jlurena.revolvingweekview.DayTime;

public class TimetableViewModel extends ViewModel {
    private static final String FORMAT_LESSON_OCCURRENCE_NAME = "%s\n[%s] %s";
    private static final String FORMAT_DATE_RANGE = "%s-%s";
    private static final String FORMAT_SINGLE_WEEK = "Week %d";
    private static final String FORMAT_CONTINUOUS_WEEKS = "Weeks %d-%d";
    private static final String FORMAT_DISCONTINUOUS_WEEKS = "Weeks %s";
    private static final String FORMAT_DATE = "MMM d";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT_DATE);

    @NonNull
    private final GetModuleReadingsUseCase getModuleReadingsUseCase;
    @NonNull
    private final GetAlternateLessonsUseCase getAlternateLessonsUseCase;
    @NonNull
    private final UpdateLessonNoUseCase updateLessonNoUseCase;
    @NonNull
    private final DeleteModuleReadingUseCase deleteModuleReadingUseCase;
    @NonNull
    private final LiveData<TimetableState> observableState;
    @NonNull
    private final Semester semester;

    public TimetableViewModel(@NonNull GetModuleReadingsUseCase getModuleReadingsUseCase,
                              @NonNull GetAlternateLessonsUseCase getAlternateLessonsUseCase,
                              @NonNull UpdateLessonNoUseCase updateLessonNoUseCase,
                              @NonNull DeleteModuleReadingUseCase deleteModuleReadingUseCase,
                              @NonNull Semester semester) {
        this.getModuleReadingsUseCase = requireNonNull(getModuleReadingsUseCase);
        this.getAlternateLessonsUseCase = requireNonNull(getAlternateLessonsUseCase);
        this.updateLessonNoUseCase = requireNonNull(updateLessonNoUseCase);
        this.deleteModuleReadingUseCase = requireNonNull(deleteModuleReadingUseCase);
        this.semester = requireNonNull(semester);
        this.observableState = Transformations.map(getModuleReadingsUseCase.execute(semester),
                TimetableViewModel::buildState);
    }

    private static TimetableState buildState(Collection<ModuleReading> moduleReadings) {
        List<UiTimetableLessonOccurrence> uiTimetableLessonOccurrences = moduleReadings.stream()
                .map(TimetableViewModel::buildTimetableOccurrences)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        List<UiModuleReading> uiModuleReadings = moduleReadings.stream()
                .map(TimetableViewModel::mapModuleReading)
                .collect(Collectors.toList());

        return new TimetableState(uiTimetableLessonOccurrences, uiModuleReadings);
    }

    private static List<UiTimetableLessonOccurrence> buildTimetableOccurrences(
            ModuleReading moduleReading) {
        String moduleCode = moduleReading.getModule().getModuleCode();
        Color color = moduleReading.getColor();
        return moduleReading.getAssignedLessons().stream()
                .map(lesson -> buildTimetableOccurrences(lesson, moduleCode, color))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static UiModuleReading mapModuleReading(ModuleReading moduleReading) {
        String moduleCode = moduleReading.getModule().getModuleCode();
        String title = moduleReading.getModule().getTitle();
        String moduleCredit = moduleReading.getModule().getModuleCredit().toString();
        String examDate = moduleReading.getExam()
                .map(Exam::getDate)
                .map(DateUtil::formatZonedDateTimeForDisplay)
                .orElse("");
        int color = moduleReading.getColor().toArgb();
        return new UiModuleReading(moduleCode, title, moduleCredit, examDate, color);
    }

    private static List<UiTimetableLessonOccurrence> buildTimetableOccurrences(Lesson lesson,
                                                                               String moduleCode,
                                                                               Color color) {
        LessonType lessonType = lesson.getLessonType();
        String lessonNo = lesson.getLessonNo();
        return lesson.getOccurrences().stream()
                .map(occurrence -> TimetableViewModel.buildTimetableLessonOccurrence(occurrence,
                        moduleCode,
                        lessonType, lessonNo, color))
                .collect(Collectors.toList());
    }

    private static UiTimetableLessonOccurrence buildTimetableLessonOccurrence(
            LessonOccurrence occurrence, String moduleCode,
            LessonType lessonType, String lessonNo, Color color) {
        DayTime startTime = convertTime(occurrence.getDay(), occurrence.getStartTime());
        DayTime endTime = convertTime(occurrence.getDay(), occurrence.getEndTime());
        String name = String.format(FORMAT_LESSON_OCCURRENCE_NAME, moduleCode,
                lessonType.getShortName(), lessonNo);
        UiTimetableLessonOccurrence uiOccurrence
                = new UiTimetableLessonOccurrence(name, occurrence.getVenue(), startTime,
                endTime, moduleCode, lessonType, lessonNo);
        uiOccurrence.setColor(color.toArgb());
        return uiOccurrence;
    }

    private static DayTime convertTime(DayOfWeek day, LocalTime time) {
        org.threeten.bp.DayOfWeek threetenDay = org.threeten.bp.DayOfWeek.valueOf(day.name());
        org.threeten.bp.LocalTime threetenTime
                = org.threeten.bp.LocalTime.of(time.getHour(), time.getMinute());
        return new DayTime(threetenDay, threetenTime);
    }

    @NonNull
    public LiveData<TimetableState> getState() {
        return observableState;
    }

    @NonNull
    public LiveData<List<UiLesson>> getAlternateLessons(String moduleCode,
                                                        LessonType lessonType,
                                                        String curLessonNo) {
        LiveData<List<Lesson>> observableLessons = getAlternateLessonsUseCase.execute(
                AcademicYear.getCurrent(), moduleCode, semester, lessonType, curLessonNo);
        return Transformations.map(observableLessons, lessons -> mapLessons(lessons, moduleCode));
    }

    private List<UiLesson> mapLessons(Collection<Lesson> lessons, String moduleCode) {
        return lessons.stream()
                .map(lesson -> mapLesson(lesson, moduleCode))
                .collect(Collectors.toList());
    }

    private UiLesson mapLesson(Lesson lesson, String moduleCode) {
        LessonType domainLessonType = lesson.getLessonType();
        String lessonType = domainLessonType.getShortName();
        String lessonNo = lesson.getLessonNo();
        List<UiLessonOccurrence> occurrences = lesson.getOccurrences().stream()
                .map(TimetableViewModel::mapLessonOccurrence)
                .collect(Collectors.toList());
        Runnable onClick = () -> updateAssignedLessonNo(moduleCode, domainLessonType, lessonNo);
        return new UiLesson(lessonType, lessonNo, occurrences, onClick);
    }

    private static UiLessonOccurrence mapLessonOccurrence(LessonOccurrence occurrence) {
        String day = occurrence.getDay().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String startTime = occurrence.getStartTime().toString();
        String endTime = occurrence.getEndTime().toString();
        String weeks = mapWeeks(occurrence.getWeeks());
        return new UiLessonOccurrence(day, startTime, endTime, weeks);
    }

    public void updateAssignedLessonNo(String moduleCode, LessonType lessonType,
                                       String newLessonNo) {
        updateLessonNoUseCase.execute(moduleCode, semester, lessonType, newLessonNo);
    }

    private static String mapWeeks(Weeks weeks) {
        if (weeks.isSingleDate()) {
            return formatWeeksAsSingleDate(weeks);
        } else if (weeks.isDateRange()) {
            return formatWeeksAsDateRange(weeks);
        } else if (weeks.isSingleWeek()) {
            return formatWeeksAsSingleWeek(weeks);
        } else if (weeks.isContinuous()) {
            return formatWeeksAsContinuousWeeks(weeks);
        } else {
            return formatWeeksAsDiscontinuousWeeks(weeks);
        }
    }

    private static String formatWeeksAsSingleDate(Weeks weeks) {
        return weeks.getStart().map(date -> date.format(FORMATTER))
                .orElseThrow(() -> new IllegalStateException("Date not available"));
    }

    private static String formatWeeksAsDateRange(Weeks weeks) {
        String startDate = weeks.getStart()
                .map(date -> date.format(FORMATTER))
                .orElseThrow(() -> new IllegalStateException("Start Date not available"));
        String endDate = weeks.getEnd()
                .map(date -> date.format(FORMATTER))
                .orElseThrow(() -> new IllegalStateException("End Date not available"));
        return String.format(FORMAT_DATE_RANGE, startDate, endDate);
    }

    private static String formatWeeksAsSingleWeek(Weeks weeks) {
        int week = weeks.firstWeek()
                .orElseThrow(() -> new IllegalStateException("Week unavailable"));
        return String.format(Locale.ENGLISH, FORMAT_SINGLE_WEEK, week);
    }

    private static String formatWeeksAsContinuousWeeks(Weeks weeks) {
        int firstWeek = weeks.firstWeek()
                .orElseThrow(() -> new IllegalStateException("Weeks not available"));
        int lastWeek = weeks.lastWeek()
                .orElseThrow(() -> new IllegalStateException("Weeks not available"));
        return String.format(Locale.ENGLISH, FORMAT_CONTINUOUS_WEEKS, firstWeek, lastWeek);
    }

    private static String formatWeeksAsDiscontinuousWeeks(Weeks weeks) {
        String delimitedWeeks = weeks.getWeeks().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        return String.format(FORMAT_DISCONTINUOUS_WEEKS, delimitedWeeks);
    }

    public void deleteModuleReading(String moduleCode) {
        deleteModuleReadingUseCase.execute(moduleCode, semester);
    }
}
