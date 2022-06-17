package com.ashuh.nusmoduleplanner.common.data.remote.model.module.prerequisitetree;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.SinglePrerequisiteTreeNode;

import java.util.Collections;
import java.util.Set;

public class ApiSinglePrerequisiteTreeNode implements ApiPrerequisiteTreeNode {
    @NonNull
    private final String prerequisite;

    public ApiSinglePrerequisiteTreeNode(@NonNull String prerequisite) {
        requireNonNull(prerequisite);
        this.prerequisite = prerequisite;
    }

    @NonNull
    public String getPrerequisite() {
        return prerequisite;
    }

    @NonNull
    @Override
    public Set<ApiPrerequisiteTreeNode> getChildren() {
        return Collections.emptySet();
    }

    @NonNull
    public SinglePrerequisiteTreeNode toDomain() {
        return new SinglePrerequisiteTreeNode(prerequisite);
    }

    @NonNull
    @Override
    public String toString() {
        return prerequisite;
    }
}
