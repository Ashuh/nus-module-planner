package com.ashuh.nusmoduleplanner.api.disqus;

import com.ashuh.nusmoduleplanner.data.disqus.PostList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DisqusApiInterface {

    @GET("threads/listPosts.json")
    Call<PostList> getPosts(@Query("api_key") String apiKey, @Query("forum") String forum,
                            @Query("thread:ident") String moduleCode);
}
