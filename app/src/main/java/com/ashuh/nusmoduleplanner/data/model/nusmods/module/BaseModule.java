package com.ashuh.nusmoduleplanner.data.model.nusmods.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

public abstract class BaseModule {

    @NonNull
    protected final String moduleCode;
    @NonNull
    protected final String title;

    public BaseModule(@NonNull String moduleCode, @NonNull String title) {
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

    @NonNull
    @Override
    public String toString() {
        return "BaseModule{"
                + "moduleCode='" + moduleCode + '\''
                + ", title='" + title + '\''
                + '}';
    }
}
