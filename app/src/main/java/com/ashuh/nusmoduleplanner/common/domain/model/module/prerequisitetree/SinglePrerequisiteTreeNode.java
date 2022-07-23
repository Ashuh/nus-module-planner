package com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class SinglePrerequisiteTreeNode implements PrerequisiteTreeNode {
    @NonNull
    private final String moduleCode;

    public SinglePrerequisiteTreeNode(@NonNull String moduleCode) {
        this.moduleCode = requireNonNull(moduleCode);
    }

    @NonNull
    @Override
    public Set<PrerequisiteTreeNode> getChildren() {
        return Collections.emptySet();
    }

    @Override
    public boolean isSatisfiedBy(Collection<String> moduleCodes) {
        return moduleCodes.contains(moduleCode);
    }

    @NonNull
    public String getPrerequisite() {
        return moduleCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SinglePrerequisiteTreeNode that = (SinglePrerequisiteTreeNode) o;
        return moduleCode.equals(that.moduleCode);
    }

    @NonNull
    @Override
    public String toString() {
        return moduleCode;
    }
}
