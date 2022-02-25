package com.ashuh.nusmoduleplanner.data.disqus;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Thread {

    @SerializedName("id")
    private long id;

    public long getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return "Thread{" +
                "id=" + id +
                '}';
    }
}
