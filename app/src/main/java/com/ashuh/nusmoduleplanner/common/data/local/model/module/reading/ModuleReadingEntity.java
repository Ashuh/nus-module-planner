package com.ashuh.nusmoduleplanner.common.data.local.model.module.reading;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleEntity;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;

@Entity(
        foreignKeys = @ForeignKey(
                entity = ModuleEntity.class,
                parentColumns = "moduleCode",
                childColumns = "moduleCode",
                onDelete = ForeignKey.RESTRICT
        ),
        indices = @Index(
                value = {"moduleCode", "semester"},
                unique = true
        )
)
public class ModuleReadingEntity {
    @NonNull
    private final String moduleCode;
    @NonNull
    private final Semester semester;
    private final int color;
    @PrimaryKey(autoGenerate = true)
    private long id;

    public ModuleReadingEntity(@NonNull String moduleCode, @NonNull Semester semester, int color) {
        this.moduleCode = requireNonNull(moduleCode);
        this.semester = requireNonNull(semester);
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getModuleCode() {
        return moduleCode;
    }

    @NonNull
    public Semester getSemester() {
        return semester;
    }

    public int getColor() {
        return color;
    }
}
