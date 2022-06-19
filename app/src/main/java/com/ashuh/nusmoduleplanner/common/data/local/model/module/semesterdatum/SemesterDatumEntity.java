package com.ashuh.nusmoduleplanner.common.data.local.model.module.semesterdatum;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleEntity;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;

import java.util.Objects;

@Entity(
        foreignKeys = @ForeignKey(
                entity = ModuleEntity.class,
                parentColumns = "moduleCode",
                childColumns = "ownerModuleCode",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(
                value = {"ownerModuleCode", "semester"},
                unique = true
        )

)
public class SemesterDatumEntity {
    @NonNull
    private final Semester semester;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String ownerModuleCode;

    public SemesterDatumEntity(@NonNull Semester semester) {
        this.semester = semester;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getOwnerModuleCode() {
        return ownerModuleCode;
    }

    public void setOwnerModuleCode(@NonNull String ownerModuleCode) {
        requireNonNull(ownerModuleCode);
        this.ownerModuleCode = ownerModuleCode;
    }

    @NonNull
    public Semester getSemester() {
        return semester;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerModuleCode, semester);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SemesterDatumEntity that = (SemesterDatumEntity) o;
        return id == that.id
                && semester == that.semester
                && Objects.equals(ownerModuleCode, that.ownerModuleCode);
    }

    @NonNull
    @Override
    public String toString() {
        return "SemesterDatumEntity{"
                + "ownerModuleCode='" + ownerModuleCode + '\''
                + ", semester=" + semester
                + ", id=" + id
                + '}';
    }
}
