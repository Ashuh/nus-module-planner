package com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Set;
import java.util.StringJoiner;

public class AndPrerequisiteTreeNode extends LogicalPrerequisiteTreeNode {
    public AndPrerequisiteTreeNode() {
        super();
    }

    public AndPrerequisiteTreeNode(@NonNull Set<PrerequisiteTreeNode> prerequisites) {
        super(prerequisites);
    }

    @Override
    public boolean isSatisfiedBy(Collection<String> moduleCodes) {
        for (PrerequisiteTreeNode childPrerequisite : getChildren()) {
            if (!childPrerequisite.isSatisfiedBy(moduleCodes)) {
                return false;
            }
        }
        return true;
    }

    @NonNull
    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(" AND ");
        children.forEach(child -> stringJoiner.add(child.toString()));
        return "(" + stringJoiner + ")";
    }
}
