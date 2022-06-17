package com.ashuh.nusmoduleplanner.common.data.api;

import com.ashuh.nusmoduleplanner.common.data.api.model.post.ApiPaginatedPosts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DisqusApi {
    String ENDPOINT_THREADS_LIST_POSTS = "threads/listPosts.json";
    String PARAMETER_API_KEY = "api_key";
    String PARAMETER_FORUM = "forum";
    String PARAMETER_THREAD = "thread:ident";

    @GET(ENDPOINT_THREADS_LIST_POSTS)
    Call<ApiPaginatedPosts> getPosts(@Query(PARAMETER_API_KEY) String apiKey,
                                     @Query(PARAMETER_FORUM) String forum,
                                     @Query(PARAMETER_THREAD) String thread);
}
