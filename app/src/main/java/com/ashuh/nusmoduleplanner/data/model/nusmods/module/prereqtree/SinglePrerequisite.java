package com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.util.Collection;

public class SinglePrerequisite implements Prerequisite {

    private final String prerequisite;

    public SinglePrerequisite(String prerequisite) {
        requireNonNull(prerequisite);
        this.prerequisite = prerequisite;
    }

    @Override
    public boolean isSatisfied(Collection<String> moduleCodes) {
        return moduleCodes.contains(prerequisite);
    }

    @NonNull
    @Override
    public String toString() {
        return prerequisite;
    }
}
