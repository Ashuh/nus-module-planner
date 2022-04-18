package com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

public class Or extends LogicalPrerequisite {

    public Or(List<Prerequisite> prerequisites) {
        super(prerequisites);
    }

    @Override
    public boolean isSatisfied(Collection<String> moduleCodes) {
        for (Prerequisite prerequisite : prerequisites) {
            if (prerequisite.isSatisfied(moduleCodes)) {
                return true;
            }
        }

        return false;
    }

    @NonNull
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" or ");
        prerequisites.forEach(prerequisite -> joiner.add(prerequisite.toString()));
        return "(" + joiner + ")";
    }
}
