package com.ashuh.nusmoduleplanner.common.data.local.model.module.prerequisite;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.ModuleEntity;

import java.util.Objects;

@Entity(
        foreignKeys = @ForeignKey(
                entity = ModuleEntity.class,
                parentColumns = "moduleCode",
                childColumns = "ownerModuleCode",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(
                value = "ownerModuleCode",
                unique = true
        )
)
public class PrerequisiteTreeEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String ownerModuleCode;

    public String getOwnerModuleCode() {
        return ownerModuleCode;
    }

    public void setOwnerModuleCode(@NonNull String ownerModuleCode) {
        this.ownerModuleCode = requireNonNull(ownerModuleCode);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerModuleCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrerequisiteTreeEntity that = (PrerequisiteTreeEntity) o;
        return id == that.id
                && Objects.equals(ownerModuleCode, that.ownerModuleCode);
    }

    @NonNull
    @Override
    public String toString() {
        return "PrerequisiteTreeEntity{"
                + "id=" + id
                + ", ownerModuleCode='" + ownerModuleCode + '\''
                + '}';
    }
}
