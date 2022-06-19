package com.ashuh.nusmoduleplanner.common.domain.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.common.domain.model.post.PaginatedPosts;

public interface PostRepository {
    @NonNull
    LiveData<PaginatedPosts> getPosts(String moduleCode);
}
