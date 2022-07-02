package com.ashuh.nusmoduleplanner.common.data.remote.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiModuleCondensed extends ApiBaseModule {
    @NonNull
    @SerializedName("semesters")
    private final List<Integer> semesters;

    public ApiModuleCondensed(String moduleCode, String title, @NonNull List<Integer> semesters) {
        super(moduleCode, title);
        this.semesters = requireNonNull(semesters);
    }

    @NonNull
    public List<Integer> getSemesters() {
        return semesters;
    }
}
