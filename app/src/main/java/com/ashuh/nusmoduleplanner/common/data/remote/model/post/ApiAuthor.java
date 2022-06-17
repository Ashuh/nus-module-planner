package com.ashuh.nusmoduleplanner.common.data.remote.model.post;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.data.remote.model.post.avatar.ApiAvatar;
import com.ashuh.nusmoduleplanner.common.domain.model.post.Author;
import com.ashuh.nusmoduleplanner.common.domain.model.post.Avatar;
import com.google.gson.annotations.SerializedName;

public class ApiAuthor {
    @NonNull
    @SerializedName("username")
    private final String username;
    @NonNull
    @SerializedName("about")
    private final String about;
    @NonNull
    @SerializedName("name")
    private final String name;
    @NonNull
    @SerializedName("profileUrl")
    private final String profileUrl;
    @NonNull
    @SerializedName("url")
    private final String url;
    @NonNull
    @SerializedName("signedUrl")
    private final String signedUrl;
    @NonNull
    @SerializedName("location")
    private final String location;
    @NonNull
    @SerializedName("joinedAt")
    private final String joinedAt;
    @NonNull
    @SerializedName("id")
    private final String id;
    @NonNull
    @SerializedName("avatar")
    private final ApiAvatar avatar;
    @SerializedName("isPowerContributor")
    private final boolean isPowerContributor;
    @SerializedName("isAnonymous")
    private final boolean isAnonymous;
    @SerializedName("isPrivate")
    private final boolean isPrivate;
    @SerializedName("isPrimary")
    private final boolean isPrimary;

    public ApiAuthor(@NonNull String username, @NonNull String about, @NonNull String name,
                     @NonNull String profileUrl, @NonNull String url, @NonNull String signedUrl,
                     @NonNull String location, @NonNull String joinedAt, @NonNull String id,
                     @NonNull ApiAvatar avatar, boolean isPowerContributor, boolean isAnonymous,
                     boolean isPrivate, boolean isPrimary) {
        requireNonNull(username);
        requireNonNull(about);
        requireNonNull(name);
        requireNonNull(profileUrl);
        requireNonNull(url);
        requireNonNull(signedUrl);
        requireNonNull(location);
        requireNonNull(joinedAt);
        requireNonNull(id);
        requireNonNull(avatar);
        this.username = username;
        this.about = about;
        this.name = name;
        this.profileUrl = profileUrl;
        this.url = url;
        this.signedUrl = signedUrl;
        this.location = location;
        this.joinedAt = joinedAt;
        this.id = id;
        this.avatar = avatar;
        this.isPowerContributor = isPowerContributor;
        this.isAnonymous = isAnonymous;
        this.isPrivate = isPrivate;
        this.isPrimary = isPrimary;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    public String getAbout() {
        return about;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public boolean getIsPowerContributor() {
        return isPowerContributor;
    }

    public boolean getIsAnonymous() {
        return isAnonymous;
    }

    @NonNull
    public String getProfileUrl() {
        return profileUrl;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    @NonNull
    public String getSignedUrl() {
        return signedUrl;
    }

    public boolean getIsPrimary() {
        return isPrimary;
    }

    @NonNull
    public String getJoinedAt() {
        return joinedAt;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public ApiAvatar getAvatar() {
        return avatar;
    }

    @NonNull
    public Author toDomain() {
        Avatar domainAvatar = avatar.toDomain();
        return new Author(id, name, url, domainAvatar);
    }
}
