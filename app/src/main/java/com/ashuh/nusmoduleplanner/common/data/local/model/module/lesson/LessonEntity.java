package com.ashuh.nusmoduleplanner.common.data.local.model.module.lesson;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.semesterdatum.SemesterDatumEntity;

import java.util.Objects;

@Entity(
        foreignKeys = @ForeignKey(
                entity = SemesterDatumEntity.class,
                parentColumns = "id",
                childColumns = "ownerId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(
                value = {"ownerId", "lessonType", "classNo"},
                unique = true
        )
)
public class LessonEntity {
    @NonNull
    private final String lessonType;
    @NonNull
    private final String classNo;
    private final int size;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long ownerId;

    public LessonEntity(@NonNull String lessonType, @NonNull String classNo, int size) {
        this.lessonType = requireNonNull(lessonType);
        this.classNo = requireNonNull(classNo);
        this.size = size;
    }

    @NonNull
    public String getLessonType() {
        return lessonType;
    }

    @NonNull
    public String getClassNo() {
        return classNo;
    }

    public int getSize() {
        return size;
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

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, lessonType, classNo, size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LessonEntity that = (LessonEntity) o;
        return id == that.id
                && ownerId == that.ownerId
                && size == that.size
                && lessonType.equals(that.lessonType)
                && classNo.equals(that.classNo);
    }

    @NonNull
    @Override
    public String toString() {
        return "LessonEntity{"
                + "lessonType='" + lessonType + '\''
                + ", classNo='" + classNo + '\''
                + ", size=" + size
                + ", id=" + id
                + ", ownerId=" + ownerId
                + '}';
    }
}
