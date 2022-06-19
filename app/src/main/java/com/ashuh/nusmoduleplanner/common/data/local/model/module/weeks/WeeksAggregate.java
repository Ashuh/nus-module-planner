package com.ashuh.nusmoduleplanner.common.data.local.model.module.weeks;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Weeks;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class WeeksAggregate {
    @NonNull
    @Embedded
    private final WeeksEntity weeksEntity;
    @NonNull
    @Relation(
            parentColumn = "id",
            entityColumn = "weekNumber",
            entity = WeekNumberEntity.class,
            associateBy = @Junction(
                    value = WeeksWeekNumberCrossRefEntity.class,
                    parentColumn = "weekId",
                    entityColumn = "weekNumber"
            )
    )
    private final List<WeekNumberEntity> weekNumbers;

    public WeeksAggregate(@NonNull WeeksEntity weeksEntity,
                          @NonNull List<WeekNumberEntity> weekNumbers) {
        requireNonNull(weeksEntity);
        requireNonNull(weekNumbers);
        this.weeksEntity = weeksEntity;
        this.weekNumbers = weekNumbers;
    }

    @NonNull
    public static WeeksAggregate fromDomain(@NonNull Weeks weeks) {
        String start = weeks.getStart()
                .map(LocalDate::toString)
                .orElse(null);
        String end = weeks.getEnd()
                .map(LocalDate::toString)
                .orElse(null);
        Integer weekInterval = weeks.getWeekInterval()
                .orElse(null);
        WeeksEntity weekEntity = new WeeksEntity(start, end, weekInterval);
        List<WeekNumberEntity> weekNumbers = weeks.getWeeks().stream()
                .map(WeekNumberEntity::new)
                .collect(Collectors.toList());
        return new WeeksAggregate(weekEntity, weekNumbers);
    }

    @NonNull
    public Weeks toDomain() {
        List<Integer> domainWeekNumbers = weekNumbers.stream()
                .map(WeekNumberEntity::getWeekNumber)
                .collect(Collectors.toList());

        String start = weeksEntity.getStart();
        String end = weeksEntity.getEnd();
        Integer weekInterval = weeksEntity.getWeekInterval();

        if (start == null || end == null || weekInterval == null) {
            return new Weeks(domainWeekNumbers);
        } else {
            return new Weeks(LocalDate.parse(start), LocalDate.parse(end), weekInterval,
                    domainWeekNumbers);
        }
    }

    @NonNull
    public WeeksEntity getWeeksEntity() {
        return weeksEntity;
    }

    @NonNull
    public List<WeekNumberEntity> getWeekNumbers() {
        return weekNumbers;
    }
}
