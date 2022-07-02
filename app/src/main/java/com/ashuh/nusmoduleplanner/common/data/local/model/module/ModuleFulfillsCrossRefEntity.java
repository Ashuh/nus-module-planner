package com.ashuh.nusmoduleplanner.common.data.local.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.util.Objects;

@Entity(
        primaryKeys = {"fulfilledModuleCode", "fulfillsModuleCode"},
        foreignKeys = @ForeignKey(
                entity = ModuleEntity.class,
                parentColumns = "moduleCode",
                childColumns = "fulfillsModuleCode",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(
                value = "fulfillsModuleCode"
        )
)
public class ModuleFulfillsCrossRefEntity {
    @NonNull
    private final String fulfilledModuleCode;
    @NonNull
    private final String fulfillsModuleCode;

    public ModuleFulfillsCrossRefEntity(@NonNull String fulfilledModuleCode,
                                        @NonNull String fulfillsModuleCode) {
        this.fulfilledModuleCode = requireNonNull(fulfilledModuleCode);
        this.fulfillsModuleCode = requireNonNull(fulfillsModuleCode);
    }

    @NonNull
    public String getFulfilledModuleCode() {
        return fulfilledModuleCode;
    }

    @NonNull
    public String getFulfillsModuleCode() {
        return fulfillsModuleCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fulfilledModuleCode, fulfillsModuleCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModuleFulfillsCrossRefEntity that = (ModuleFulfillsCrossRefEntity) o;
        return fulfilledModuleCode.equals(that.fulfilledModuleCode)
                && fulfillsModuleCode.equals(that.fulfillsModuleCode);
    }

    @NonNull
    @Override
    public String toString() {
        return "ModuleRequirementCrossRefEntity{"
                + "fulfilledModuleCode='" + fulfilledModuleCode + '\''
                + ", fulfillsModuleCode='" + fulfillsModuleCode + '\''
                + '}';
    }
}
