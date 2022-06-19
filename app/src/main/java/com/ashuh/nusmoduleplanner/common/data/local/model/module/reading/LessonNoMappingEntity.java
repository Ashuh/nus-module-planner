package com.ashuh.nusmoduleplanner.common.data.local.model.module.reading;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
    private final String lessonType;
    @NonNull
    private final String lessonNo;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long ownerId;

    public LessonNoMappingEntity(@NonNull String lessonType, @NonNull String lessonNo) {
        requireNonNull(lessonType);
        requireNonNull(lessonNo);
        this.lessonType = lessonType;
        this.lessonNo = lessonNo;
    }

    @NonNull
    public String getLessonType() {
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
