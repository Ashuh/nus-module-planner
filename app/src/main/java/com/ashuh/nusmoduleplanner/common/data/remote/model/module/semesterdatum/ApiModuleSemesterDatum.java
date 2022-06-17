package com.ashuh.nusmoduleplanner.common.data.remote.model.module.semesterdatum;

import static java.util.Objects.requireNonNullElse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashuh.nusmoduleplanner.common.data.remote.model.module.ApiLesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Exam;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleSemesterDatum;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonMap;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class ApiModuleSemesterDatum extends ApiModuleInfoSemesterDatum {
    @Nullable
    @SerializedName("timetable")
    private final List<ApiLesson> timetable;

    public ApiModuleSemesterDatum(int semester, String examDate, Integer examDuration,
                                  @Nullable List<ApiLesson> timetable) {
        super(semester, examDate, examDuration);
        this.timetable = timetable;
    }

    @NonNull
    public ModuleSemesterDatum toDomain() {
        Semester domainSemester = mapSemester(semester);
        Exam domainExam = mapExam(examDate, examDuration);

        LessonMap lessonMap = new LessonMap();
        getTimetable().stream()
                .map(ApiLesson::toDomain)
                .forEach(lessonMap::put);

        return new ModuleSemesterDatum(domainSemester, domainExam, lessonMap);
    }

    @NonNull
    public List<ApiLesson> getTimetable() {
        return requireNonNullElse(timetable, Collections.emptyList());
    }
}
