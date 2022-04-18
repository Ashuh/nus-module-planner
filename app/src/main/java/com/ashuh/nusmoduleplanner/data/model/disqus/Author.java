package com.ashuh.nusmoduleplanner.data.model.disqus;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Author {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }
}
