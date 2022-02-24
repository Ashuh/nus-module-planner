package com.ashuh.nusmoduleplanner.data.disqus;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostList {

    @SerializedName("code")
    private int code;

    @SerializedName("response")
    List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    @NonNull
    @Override
    public String toString() {
        return "PostList{" +
                "code=" + code +
                ", posts=" + posts +
                '}';
    }
}
