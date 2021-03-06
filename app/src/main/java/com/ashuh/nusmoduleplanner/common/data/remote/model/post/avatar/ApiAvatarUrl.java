package com.ashuh.nusmoduleplanner.common.data.remote.model.post.avatar;


import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ApiAvatarUrl {
    @NonNull
    @SerializedName("permalink")
    protected final String permalink;
    @NonNull
    @SerializedName("cache")
    protected final String cache;

    public ApiAvatarUrl(@NonNull String permalink, @NonNull String cache) {
        this.permalink = requireNonNull(permalink);
        this.cache = requireNonNull(cache);
    }

    @NonNull
    public String getPermalink() {
        return permalink;
    }

    @NonNull
    public String getCache() {
        return cache;
    }
}
