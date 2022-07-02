package com.ashuh.nusmoduleplanner.common.domain.model.post;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

public class Author {
    @NonNull
    private final String name;
    @NonNull
    private final String userName;
    @NonNull
    private final String id;
    @NonNull
    private final String profileUrl;
    @NonNull
    private final Avatar avatar;

    public Author(@NonNull String name, @NonNull String userName, @NonNull String id,
                  @NonNull String profileUrl, @NonNull Avatar avatar) {
        this.name = requireNonNull(name);
        this.userName = requireNonNull(userName);
        this.id = requireNonNull(id);
        this.profileUrl =  requireNonNull(profileUrl);
        this.avatar = requireNonNull(avatar);
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getProfileUrl() {
        return profileUrl;
    }

    @NonNull
    public Avatar getAvatar() {
        return avatar;
    }
}
