package com.ashuh.nusmoduleplanner.common.domain.model.post;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

public class Author {
    @NonNull
    private final String name;
    @NonNull
    private final String profileUrl;
    @NonNull
    private final String id;
    @NonNull
    private final Avatar avatar;

    public Author(@NonNull String name, @NonNull String profileUrl, @NonNull String id,
                  @NonNull Avatar avatar) {
        requireNonNull(name);
        requireNonNull(profileUrl);
        requireNonNull(id);
        requireNonNull(avatar);
        this.name = name;
        this.profileUrl = profileUrl;
        this.id = id;
        this.avatar = avatar;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getProfileUrl() {
        return profileUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public Avatar getAvatar() {
        return avatar;
    }
}
