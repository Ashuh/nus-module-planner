package com.ashuh.nusmoduleplanner.common.data.local.model.module.semesterdatum;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.ExamEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.lesson.LessonAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.lesson.LessonEntity;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Exam;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleSemesterDatum;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SemesterDatumAggregate {
    @NonNull
    @Embedded
    private final SemesterDatumEntity semesterDatum;
    @Nullable
    @Relation(
            parentColumn = "id",
            entityColumn = "ownerId"
    )
    private final ExamEntity exam;
    @NonNull
    @Relation(
            parentColumn = "id",
            entityColumn = "ownerId",
            entity = LessonEntity.class
    )
    private final List<LessonAggregate> lessonAggregates;

    public SemesterDatumAggregate(@NonNull SemesterDatumEntity semesterDatum,
                                  @Nullable ExamEntity exam,
                                  @NonNull List<LessonAggregate> lessonAggregates) {
        this.semesterDatum = requireNonNull(semesterDatum);
        this.exam = exam;
        this.lessonAggregates =  requireNonNull(lessonAggregates);
    }

    public static SemesterDatumAggregate fromDomain(@NonNull ModuleSemesterDatum semesterDatum) {
        SemesterDatumEntity semesterDatumEntity
                = new SemesterDatumEntity(semesterDatum.getSemester());

        ExamEntity examEntity = semesterDatum.getExam()
                .map(ExamEntity::fromDomain)
                .orElse(null);

        List<LessonAggregate> lessonEntities = semesterDatum.getAllLessons().stream()
                .map(LessonAggregate::fromDomain)
                .collect(Collectors.toList());

        return new SemesterDatumAggregate(semesterDatumEntity, examEntity, lessonEntities);
    }

    @NonNull
    public ModuleSemesterDatum toDomain() {
        Semester semester = semesterDatum.getSemester();

        Exam domainExam = Optional.ofNullable(exam)
                .map(ExamEntity::toDomain)
                .orElse(null);

        List<Lesson> domainLessons = lessonAggregates.stream()
                .map(LessonAggregate::toDomain)
                .collect(Collectors.toList());

        return new ModuleSemesterDatum(semester, domainExam, LessonMap.of(domainLessons));
    }

    @NonNull
    public SemesterDatumEntity getSemesterDatum() {
        return semesterDatum;
    }

    @NonNull
    public Optional<ExamEntity> getExam() {
        return Optional.ofNullable(exam);
    }

    @NonNull
    public List<LessonAggregate> getLessonAggregates() {
        return lessonAggregates;
    }

    @NonNull
    @Override
    public String toString() {
        return "SemesterDatumAggregate{"
                + "semesterDatum=" + semesterDatum
                + ", exam=" + exam
                + ", lessonAggregates=" + lessonAggregates
                + '}';
    }
}
