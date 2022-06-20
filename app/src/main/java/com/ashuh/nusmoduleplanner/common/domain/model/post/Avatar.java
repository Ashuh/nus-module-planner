package com.ashuh.nusmoduleplanner.common.domain.model.post;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

public class Avatar {
    @NonNull
    private final String urlSmall;
    @NonNull
    private final String urlLarge;
    @NonNull
    private final String urlXLarge;

    public Avatar(@NonNull String urlSmall, @NonNull String urlLarge, @NonNull String urlXLarge) {
        requireNonNull(urlSmall);
        requireNonNull(urlLarge);
        requireNonNull(urlXLarge);
        this.urlSmall = urlSmall;
        this.urlLarge = urlLarge;
        this.urlXLarge = urlXLarge;
    }

    @NonNull
    public String getUrlSmall() {
        return urlSmall;
    }

    @NonNull
    public String getUrlLarge() {
        return urlLarge;
    }

    @NonNull
    public String getUrlXLarge() {
        return urlXLarge;
    }
}
