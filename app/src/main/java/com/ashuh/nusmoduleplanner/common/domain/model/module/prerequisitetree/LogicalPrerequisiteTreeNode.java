package com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Objects;
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

    @Override
    public int hashCode() {
        return Objects.hash(children);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LogicalPrerequisiteTreeNode that = (LogicalPrerequisiteTreeNode) o;
        return children.equals(that.children);
    }
}
