package com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

public abstract class LogicalPrerequisiteTreeNode implements PrerequisiteTreeNode {
    @NonNull
    protected final Set<PrerequisiteTreeNode> children;

    LogicalPrerequisiteTreeNode() {
        this.children = new HashSet<>();
    }

    LogicalPrerequisiteTreeNode(@NonNull Set<PrerequisiteTreeNode> children) {
        this.children = requireNonNull(children);
    }

    @NonNull
    public Set<PrerequisiteTreeNode> getChildren() {
        return children;
    }

    public void addChild(@NonNull PrerequisiteTreeNode child) {
        requireNonNull(child);
        children.add(child);
    }
}
