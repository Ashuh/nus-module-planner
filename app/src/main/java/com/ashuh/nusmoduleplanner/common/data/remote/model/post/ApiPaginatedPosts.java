package com.ashuh.nusmoduleplanner.common.data.remote.model.post;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.post.Cursor;
import com.ashuh.nusmoduleplanner.common.domain.model.post.PaginatedPosts;
import com.ashuh.nusmoduleplanner.common.domain.model.post.Post;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.stream.Collectors;

public class ApiPaginatedPosts {
    @NonNull
    @SerializedName("cursor")
    private final ApiCursor cursor;
    @NonNull
    @SerializedName("response")
    private final List<ApiPost> posts;

    public ApiPaginatedPosts(@NonNull ApiCursor cursor, @NonNull List<ApiPost> posts) {
        this.cursor = requireNonNull(cursor);
        this.posts = requireNonNull(posts);
    }

    @NonNull
    public ApiCursor getCursor() {
        return cursor;
    }

    @NonNull
    public List<ApiPost> getPosts() {
        return posts;
    }

    @NonNull
    public PaginatedPosts toDomain() {
        Cursor domainCursor = cursor.toDomain();
        List<Post> domainPosts = posts.stream()
                .map(ApiPost::toDomain)
                .collect(Collectors.toList());
        return new PaginatedPosts(domainCursor, domainPosts);

    }
}
