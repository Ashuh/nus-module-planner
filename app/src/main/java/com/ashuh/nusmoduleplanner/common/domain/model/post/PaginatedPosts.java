package com.ashuh.nusmoduleplanner.common.domain.model.post;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.util.List;

public class PaginatedPosts {
    @NonNull
    private final Cursor cursor;
    @NonNull
    private final List<Post> posts;

    public PaginatedPosts(@NonNull Cursor cursor, @NonNull List<Post> posts) {
        this.cursor = requireNonNull(cursor);
        this.posts = requireNonNull(posts);
    }

    @NonNull
    public Cursor getCursor() {
        return cursor;
    }

    @NonNull
    public List<Post> getPosts() {
        return posts;
    }
}
