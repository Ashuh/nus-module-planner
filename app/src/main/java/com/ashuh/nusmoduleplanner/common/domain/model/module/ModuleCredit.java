package com.ashuh.nusmoduleplanner.common.domain.model.module;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.util.StringUtil;

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
        return StringUtil.doubleWithoutTrailingZeros(value) + " MCs";
    }
}
