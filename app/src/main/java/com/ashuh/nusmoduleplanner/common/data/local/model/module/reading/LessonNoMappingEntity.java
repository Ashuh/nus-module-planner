package com.ashuh.nusmoduleplanner.common.data.local.model.module.reading;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;

@Entity(
        foreignKeys = @ForeignKey(
                entity = ModuleReadingEntity.class,
                parentColumns = "id",
                childColumns = "ownerId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(
                value = {"ownerId", "lessonType"},
                unique = true
        )
)
public class LessonNoMappingEntity {
    @NonNull
    private final LessonType lessonType;
    @NonNull
    private final String lessonNo;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long ownerId;

    public LessonNoMappingEntity(@NonNull LessonType lessonType, @NonNull String lessonNo) {
        this.lessonType = requireNonNull(lessonType);
        this.lessonNo = requireNonNull(lessonNo);
    }

    @NonNull
    public LessonType getLessonType() {
        return lessonType;
    }

    @NonNull
    public String getLessonNo() {
        return lessonNo;
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
}
