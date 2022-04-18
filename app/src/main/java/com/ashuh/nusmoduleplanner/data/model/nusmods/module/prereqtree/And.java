package com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

public class And extends LogicalPrerequisite {

    public And(List<Prerequisite> prerequisites) {
        super(prerequisites);
    }

    @Override
    public boolean isSatisfied(Collection<String> moduleCodes) {
        for (Prerequisite prerequisite : prerequisites) {
            if (!prerequisite.isSatisfied(moduleCodes)) {
                return false;
            }
        }

        return true;
    }

    @NonNull
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" and ");
        prerequisites.forEach(prerequisite -> joiner.add(prerequisite.toString()));
        return "(" + joiner + ")";
    }
}
