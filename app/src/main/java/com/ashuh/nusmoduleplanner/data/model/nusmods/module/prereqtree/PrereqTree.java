package com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree;

import androidx.annotation.NonNull;

public class PrereqTree {

    private SinglePrerequisite singlePrerequisite;
    private LogicalPrerequisite logicalPrerequisite;

    public PrereqTree(LogicalPrerequisite logicalPrerequisite) {
        this.logicalPrerequisite = logicalPrerequisite;
    }

    public PrereqTree(SinglePrerequisite singlePrerequisite) {
        this.singlePrerequisite = singlePrerequisite;
    }

    @NonNull
    @Override
    public String toString() {
        return "PrereqTree{" + (hasMultiplePrerequisites() ? logicalPrerequisite :
                singlePrerequisite);
    }

    public boolean hasMultiplePrerequisites() {
        return logicalPrerequisite != null;
    }
}
