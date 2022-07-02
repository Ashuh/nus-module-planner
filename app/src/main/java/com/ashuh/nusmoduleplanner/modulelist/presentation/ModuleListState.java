package com.ashuh.nusmoduleplanner.modulelist.presentation;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.modulelist.presentation.model.UiModuleInfo;

import java.util.List;

public class ModuleListState {
    @NonNull
    private final List<UiModuleInfo> modules;

    public ModuleListState(@NonNull List<UiModuleInfo> modules) {
        this.modules = requireNonNull(modules);
    }

    @NonNull
    public List<UiModuleInfo> getModules() {
        return modules;
    }
}
