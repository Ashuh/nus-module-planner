package com.ashuh.nusmoduleplanner.common.data.api.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public abstract class ApiBaseModule {
    @NonNull
    @SerializedName("moduleCode")
    protected final String moduleCode;
    @NonNull
    @SerializedName("title")
    protected final String title;

    public ApiBaseModule(@NonNull String moduleCode, @NonNull String title) {
        requireNonNull(moduleCode);
        requireNonNull(title);
        this.moduleCode = moduleCode;
        this.title = title;
    }

    @NonNull
    public String getModuleCode() {
        return moduleCode;
    }

    @NonNull
    public String getTitle() {
        return title;
    }
}
