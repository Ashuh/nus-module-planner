package com.ashuh.nusmoduleplanner.moduledetail.domain.model;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.post.PaginatedPosts;

import java.util.Optional;

public class ModuleWithPosts extends Pair<Optional<Module>, Optional<PaginatedPosts>> {
    public ModuleWithPosts(@Nullable Module first, @Nullable PaginatedPosts second) {
        super(Optional.ofNullable(first), Optional.ofNullable(second));
    }

    @NonNull
    public Optional<Module> getModule() {
        return this.first;
    }

    @NonNull
    public Optional<PaginatedPosts> getPaginatedPosts() {
        return this.second;
    }
}
