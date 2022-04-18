package com.ashuh.nusmoduleplanner.data.model.nusmods.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.util.List;

public class ModuleCondensed extends BaseModule {

    @NonNull
    private final List<Integer> semesters;

    public ModuleCondensed(String moduleCode, String title, @NonNull List<Integer> semesters) {
        super(moduleCode, title);
        requireNonNull(semesters);
        this.semesters = semesters;
    }

    @NonNull
    @Override
    public String toString() {
        return "ModuleCondensed{" +
                "moduleCode='" + moduleCode + '\'' +
                ", title='" + title + '\'' +
                ", semesters=" + semesters +
                '}';
    }
}
