package com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class SinglePrerequisiteTreeNode implements PrerequisiteTreeNode {
    @NonNull
    private final String moduleCode;

    public SinglePrerequisiteTreeNode(@NonNull String moduleCode) {
        requireNonNull(moduleCode);
        this.moduleCode = moduleCode;
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

    @NonNull
    @Override
    public String toString() {
        return moduleCode;
    }
}
