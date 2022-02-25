package com.ashuh.nusmoduleplanner.data.source;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ashuh.nusmoduleplanner.BuildConfig;
import com.ashuh.nusmoduleplanner.data.model.disqus.PostList;
import com.ashuh.nusmoduleplanner.data.model.disqus.ThreadDetails;
import com.ashuh.nusmoduleplanner.data.source.remote.disqus.DisqusApiInterface;
import com.ashuh.nusmoduleplanner.data.source.remote.disqus.DisqusApiInterfaceBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisqusRepository {

    private static final String KEY = BuildConfig.DISQUS_KEY;
    private static final String FORUM = "nusmods-prod";
    private static final String TAG = "DisqusRepository";

    private static DisqusRepository instance;

    public static DisqusRepository getInstance() {
        if (instance == null) {
            instance = new DisqusRepository();
        }
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LiveData<ThreadDetails> getThreadDetails(String moduleCode) {
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
    public LiveData<PostList> getPosts(String moduleCode) {
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