package com.ashuh.nusmoduleplanner.common.data.repository;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.ashuh.nusmoduleplanner.BuildConfig;
import com.ashuh.nusmoduleplanner.common.data.remote.source.PostRemoteDataSource;
import com.ashuh.nusmoduleplanner.common.domain.model.post.PaginatedPosts;
import com.ashuh.nusmoduleplanner.common.domain.repository.PostRepository;

public class AppPostRepository implements PostRepository {
    private static final String API_KEY = BuildConfig.DISQUS_KEY;
    private static final String FORUM = "nusmods-prod";

    @NonNull
    private final PostRemoteDataSource postRemoteDataSource;

    public AppPostRepository(@NonNull PostRemoteDataSource postRemoteDataSource) {
        requireNonNull(postRemoteDataSource);
        this.postRemoteDataSource = postRemoteDataSource;
    }

    @NonNull
    @Override
    public LiveData<PaginatedPosts> getPosts(String moduleCode) {
        return postRemoteDataSource.getPosts(moduleCode);
    }
}
