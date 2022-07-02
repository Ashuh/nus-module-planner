package com.ashuh.nusmoduleplanner.common.data.local.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.semesterdatum.SemesterDatumEntity;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Exam;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity(
        foreignKeys = @ForeignKey(
                entity = SemesterDatumEntity.class,
                parentColumns = "id",
                childColumns = "ownerId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(
                value = "ownerId",
                unique = true
        )
)
public class ExamEntity {
    @NonNull
    private final String date;
    private final int duration;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long ownerId;

    public ExamEntity(@NonNull String date, int duration) {
        this.date = requireNonNull(date);
        this.duration = duration;
    }

    @NonNull
    public static ExamEntity fromDomain(@NonNull Exam exam) {
        String date = exam.getDate().toString();
        int duration = (int) exam.getDuration().toMinutes();
        return new ExamEntity(date, duration);
    }

    @NonNull
    public Exam toDomain() {
        ZonedDateTime domainDate = ZonedDateTime.parse(date);
        Duration domainDuration = Duration.ofMinutes(duration);
        return new Exam(domainDate, domainDuration);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, date, duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExamEntity that = (ExamEntity) o;
        return id == that.id
                && ownerId == that.ownerId
                && duration == that.duration
                && date.equals(that.date);
    }

    @NonNull
    @Override
    public String toString() {
        return "ExamEntity{"
                + "date='" + date + '\''
                + ", duration=" + duration
                + ", id=" + id
                + ", ownerId=" + ownerId
                + '}';
    }
}
