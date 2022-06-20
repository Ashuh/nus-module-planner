package com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Set;
import java.util.StringJoiner;

public class OrPrerequisiteTreeNode extends LogicalPrerequisiteTreeNode {
    public OrPrerequisiteTreeNode() {
        super();
    }

    public OrPrerequisiteTreeNode(@NonNull Set<PrerequisiteTreeNode> prerequisites) {
        super(prerequisites);
    }

    @Override
    public boolean isSatisfiedBy(Collection<String> moduleCodes) {
        for (PrerequisiteTreeNode child : children) {
            if (child.isSatisfiedBy(moduleCodes)) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(" OR ");
        children.forEach(child -> stringJoiner.add(child.toString()));
        return "(" + stringJoiner + ")";
    }
}
