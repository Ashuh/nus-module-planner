package com.ashuh.nusmoduleplanner.moduledetail.presentation;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiModule;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiPost;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ModuleDetailState {
    @Nullable
    private final UiModule module;
    @NonNull
    private final List<UiPost> posts;

    public ModuleDetailState(@Nullable UiModule module, @NonNull List<UiPost> posts) {
        this.module = module;
        this.posts = requireNonNull(posts);
    }

    public Optional<UiModule> getModule() {
        return Optional.ofNullable(module);
    }

    @NonNull
    public List<UiPost> getPosts() {
        return posts;
    }

    public static class Builder {
        private UiModule module = null;
        private List<UiPost> posts = Collections.emptyList();

        public void setModule(UiModule module) {
            this.module = module;
        }

        public void setPosts(List<UiPost> posts) {
            this.posts = posts;
        }

        public ModuleDetailState build() {
            return new ModuleDetailState(module, posts);
        }
    }
}
