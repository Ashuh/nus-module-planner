package com.ashuh.nusmoduleplanner.common.data.remote.source;

import static java.util.Objects.requireNonNull;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class LiveDataCallback<T> implements Callback<T> {
    @NonNull
    private final MutableLiveData<T> livedata;

    LiveDataCallback(@NonNull MutableLiveData<T> livedata) {
        requireNonNull(livedata);
        this.livedata = livedata;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, Response<T> response) {
        if (!response.isSuccessful() || response.body() == null) {
            onFailure(call, new HttpException(response));
            return;
        }
        livedata.setValue(response.body());
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        Log.d("LiveDataCallback", "onFailure: " + t.getMessage());
    }
}
