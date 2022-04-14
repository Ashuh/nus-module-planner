package com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree;

import androidx.annotation.NonNull;

import java.util.List;

public abstract class LogicalPrerequisite implements Prerequisite {

    @NonNull
    protected final List<Prerequisite> prerequisites;

    public LogicalPrerequisite(@NonNull List<Prerequisite> prerequisites) {
        this.prerequisites = prerequisites;
    }

    @NonNull
    public List<Prerequisite> getPrerequisites() {
        return prerequisites;
    }
}
