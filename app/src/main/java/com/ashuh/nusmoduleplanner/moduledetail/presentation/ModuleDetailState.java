package com.ashuh.nusmoduleplanner.moduledetail.presentation;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

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
    private final boolean isLoadingModule;
    private final boolean isLoadingPosts;

    public ModuleDetailState(@Nullable UiModule module, @NonNull List<UiPost> posts,
                             boolean isLoadingModule, boolean isLoadingPosts) {
        this.module = module;
        this.posts = requireNonNull(posts);
        this.isLoadingModule = isLoadingModule;
        this.isLoadingPosts = isLoadingPosts;
    }

    public static ModuleDetailState loading() {
        return new ModuleDetailState(null, Collections.emptyList(), true, true);
    }

    public Optional<UiModule> getModule() {
        return Optional.ofNullable(module);
    }

    @NonNull
    public List<UiPost> getPosts() {
        return posts;
    }

    public boolean isLoadingModule() {
        return isLoadingModule;
    }

    public boolean isLoadingPosts() {
        return isLoadingPosts;
    }

    public static class Builder {
        private UiModule module = null;
        private List<UiPost> posts = null;

        public void setModule(UiModule module) {
            this.module = module;
        }

        public void setPosts(List<UiPost> posts) {
            this.posts = posts;
        }

        public ModuleDetailState build() {
            boolean isLoadingModule = module == null;
            boolean isLoadingPosts = posts == null;
            posts = requireNonNullElse(posts, Collections.emptyList());
            return new ModuleDetailState(module, posts, isLoadingModule, isLoadingPosts);
        }
    }
}
