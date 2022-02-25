package com.ashuh.nusmoduleplanner.api.disqus;

import com.ashuh.nusmoduleplanner.data.disqus.PostList;
import com.ashuh.nusmoduleplanner.data.disqus.ThreadDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DisqusApiInterface {

    @GET("posts/list.json")
    Call<PostList> getPosts(@Query("api_key") String key, @Query("forum") String forum, @Query(
            "thread") long thread);

    @GET("threads/details.json")
    Call<ThreadDetails> getThreadDetails(@Query("api_key") String apiKey, @Query("forum") String forum,
                                         @Query("thread:ident") String moduleCode);
}
