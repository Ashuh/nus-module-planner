package com.ashuh.nusmoduleplanner.common.data.remote.source;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.ashuh.nusmoduleplanner.BuildConfig;
import com.ashuh.nusmoduleplanner.common.data.remote.api.DisqusApi;
import com.ashuh.nusmoduleplanner.common.data.remote.model.post.ApiPaginatedPosts;
import com.ashuh.nusmoduleplanner.common.domain.model.post.PaginatedPosts;

public class PostRemoteDataSource {
    private static final String API_KEY = BuildConfig.DISQUS_KEY;
    private static final String FORUM_ID = "nusmods-prod";

    @NonNull
    private final DisqusApi disqusApi;

    public PostRemoteDataSource(@NonNull DisqusApi disqusApi) {
        this.disqusApi = requireNonNull(disqusApi);
    }

    @NonNull
    public LiveData<PaginatedPosts> getPosts(@NonNull String moduleCode) {
        MutableLiveData<ApiPaginatedPosts> observablePosts = new MutableLiveData<>();
        disqusApi.getPosts(API_KEY, FORUM_ID, moduleCode)
                .enqueue(new LiveDataCallback<>(observablePosts));
        return Transformations.map(observablePosts, ApiPaginatedPosts::toDomain);
    }
}
