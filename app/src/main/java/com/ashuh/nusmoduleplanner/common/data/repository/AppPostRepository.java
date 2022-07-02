package com.ashuh.nusmoduleplanner.common.data.repository;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.common.data.remote.source.PostRemoteDataSource;
import com.ashuh.nusmoduleplanner.common.domain.model.post.PaginatedPosts;
import com.ashuh.nusmoduleplanner.common.domain.repository.PostRepository;

public class AppPostRepository implements PostRepository {
    @NonNull
    private final PostRemoteDataSource postRemoteDataSource;

    public AppPostRepository(@NonNull PostRemoteDataSource postRemoteDataSource) {
        this.postRemoteDataSource = requireNonNull(postRemoteDataSource);
    }

    @NonNull
    @Override
    public LiveData<PaginatedPosts> getPosts(@NonNull String moduleCode) {
        requireNonNull(moduleCode);
        return postRemoteDataSource.getPosts(moduleCode);
    }
}
