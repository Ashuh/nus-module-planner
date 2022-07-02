package com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Set;

public interface PrerequisiteTreeNode {
    @NonNull
    Set<PrerequisiteTreeNode> getChildren();

    boolean isSatisfiedBy(Collection<String> moduleCodes);
}
