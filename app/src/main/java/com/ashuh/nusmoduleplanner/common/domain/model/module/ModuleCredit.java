package com.ashuh.nusmoduleplanner.common.domain.model.module;

import androidx.annotation.NonNull;

public class ModuleCredit {
    private final double value;

    public ModuleCredit(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return value + " MCs";
    }
}
