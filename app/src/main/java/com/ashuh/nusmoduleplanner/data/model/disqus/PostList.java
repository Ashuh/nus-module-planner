package com.ashuh.nusmoduleplanner.data.model.disqus;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostList {

    @SerializedName("response")
    List<Post> posts;
    @SerializedName("code")
    private int code;

    public List<Post> getPosts() {
        return posts;
    }

    @NonNull
    @Override
    public String toString() {
        return "PostList{"
                + "code=" + code
                + ", posts=" + posts
                + '}';
    }
}
