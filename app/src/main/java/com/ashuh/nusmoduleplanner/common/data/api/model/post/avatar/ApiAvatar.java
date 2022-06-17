package com.ashuh.nusmoduleplanner.common.data.api.model.post.avatar;


import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.post.Avatar;
import com.google.gson.annotations.SerializedName;

public class ApiAvatar extends ApiAvatarUrl {
    @NonNull
    @SerializedName("xlarge")
    private final ApiAvatarUrl xLarge;
    @NonNull
    @SerializedName("large")
    private final ApiAvatarUrl large;
    @NonNull
    @SerializedName("small")
    private final ApiAvatarUrl small;
    @SerializedName("isCustom")
    private final boolean isCustom;

    public ApiAvatar(String permalink, String cache, @NonNull ApiAvatarUrl xLarge,
                     @NonNull ApiAvatarUrl large, @NonNull ApiAvatarUrl small, boolean isCustom) {
        super(permalink, cache);
        requireNonNull(xLarge);
        requireNonNull(large);
        requireNonNull(small);
        this.xLarge = xLarge;
        this.large = large;
        this.small = small;
        this.isCustom = isCustom;
    }

    @NonNull
    public ApiAvatarUrl getXLarge() {
        return xLarge;
    }

    @NonNull
    public ApiAvatarUrl getLarge() {
        return large;
    }

    @NonNull
    public ApiAvatarUrl getSmall() {
        return small;
    }

    public boolean getIsCustom() {
        return isCustom;
    }

    @NonNull
    public Avatar toDomain() {
        String domainSmall = small.getCache();
        String domainLarge = large.getCache();
        String domainXLarge = xLarge.getCache();
        return new Avatar(domainSmall, domainLarge, domainXLarge);
    }
}
