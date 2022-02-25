package com.ashuh.nusmoduleplanner.data;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ashuh.nusmoduleplanner.BuildConfig;
import com.ashuh.nusmoduleplanner.api.disqus.DisqusApiInterface;
import com.ashuh.nusmoduleplanner.api.disqus.DisqusApiInterfaceBuilder;
import com.ashuh.nusmoduleplanner.data.disqus.PostList;
import com.ashuh.nusmoduleplanner.data.disqus.ThreadDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisqusRepository {

    private static final String KEY = BuildConfig.DISQUS_KEY;
    private static final String FORUM = "nusmods-prod";
    private static final String TAG = "DisqusRepository";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LiveData<ThreadDetails> getThreadDetails(String moduleCode) {
        MutableLiveData<ThreadDetails> threadDetailsLiveData = new MutableLiveData<>();
        DisqusApiInterface api = DisqusApiInterfaceBuilder.getApiInterface();
        Call<ThreadDetails> call = api.getThreadDetails(KEY, FORUM, moduleCode);
        call.enqueue(new Callback<ThreadDetails>() {
            @Override
            public void onResponse(@NonNull Call<ThreadDetails> call,
                                   @NonNull Response<ThreadDetails> response) {
                threadDetailsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ThreadDetails> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });

        return threadDetailsLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LiveData<PostList> getPosts(String moduleCode) {
        MutableLiveData<PostList> postListLiveData = new MutableLiveData<>();
        getThreadDetails(moduleCode).observeForever(threadDetails -> {
            DisqusApiInterface api = DisqusApiInterfaceBuilder.getApiInterface();
            Call<PostList> call = api.getPosts(KEY, FORUM, threadDetails.getThread().getId());

            call.enqueue(new Callback<PostList>() {
                @Override
                public void onResponse(@NonNull Call<PostList> call,
                                       @NonNull Response<PostList> response) {
                    postListLiveData.setValue(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<PostList> call, @NonNull Throwable t) {
                    Log.d(TAG, "onFailure: " + t);
                }
            });
        });

        return postListLiveData;
    }
}
