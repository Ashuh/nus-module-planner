package com.ashuh.nusmoduleplanner.data.disqus;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ThreadDetails {

    @SerializedName("code")
    private int code;

    @SerializedName("response")
    Thread thread;

    public int getCode() {
        return code;
    }

    public Thread getThread() {
        return thread;
    }

    @NonNull
    @Override
    public String toString() {
        return "ThreadDetails{" +
                "code=" + code +
                ", thread=" + thread +
                '}';
    }
}
