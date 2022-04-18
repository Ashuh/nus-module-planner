package com.ashuh.nusmoduleplanner.data.source.disqus;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ashuh.nusmoduleplanner.BuildConfig;
import com.ashuh.nusmoduleplanner.api.disqus.DisqusApiInterface;
import com.ashuh.nusmoduleplanner.api.disqus.DisqusApiInterfaceBuilder;
import com.ashuh.nusmoduleplanner.data.model.disqus.PostList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisqusRepository {

    private static final String KEY = BuildConfig.DISQUS_KEY;
    private static final String FORUM = "nusmods-prod";
    private static final String TAG = "DisqusRepository";

    public static LiveData<PostList> getPosts(String moduleCode) {
        MutableLiveData<PostList> liveData = new MutableLiveData<>();
        DisqusApiInterface api = DisqusApiInterfaceBuilder.getApiInterface();
        Call<PostList> call = api.getPosts(KEY, FORUM, moduleCode);
        call.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(@NonNull Call<PostList> call,
                                   @NonNull Response<PostList> response) {
                liveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<PostList> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });

        return liveData;
    }
}
