package com.ashuh.nusmoduleplanner.modulelist.presentation;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.modulelist.presentation.model.UiModuleInfo;

import java.util.Collections;
import java.util.List;

public class ModuleListState {
    @NonNull
    private final List<UiModuleInfo> modules;
    private final boolean isLoading;

    public ModuleListState(@NonNull List<UiModuleInfo> modules, boolean isLoading) {
        this.modules = requireNonNull(modules);
        this.isLoading = isLoading;
    }

    public static ModuleListState loading() {
        return new ModuleListState(Collections.emptyList(), true);
    }

    public static ModuleListState loaded(@NonNull List<UiModuleInfo> modules) {
        return new ModuleListState(modules, false);
    }

    @NonNull
    public List<UiModuleInfo> getModules() {
        return modules;
    }

    public boolean isLoading() {
        return isLoading;
    }
}
