package com.ashuh.nusmoduleplanner.common.data.remote.model.post;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.post.Cursor;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ApiCursor {
    @NonNull
    @SerializedName("next")
    private final String next;
    @NonNull
    @SerializedName("prev")
    private final String prev;
    @SerializedName("hasPrev")
    private final boolean hasPrev;
    @SerializedName("hasNext")
    private final boolean hasNext;
    @SerializedName("total")
    private final int total;

    public ApiCursor(@NonNull String prev, @NonNull String next, boolean hasPrev, boolean hasNext,
                     int total) {
        Objects.requireNonNull(prev);
        Objects.requireNonNull(next);
        this.prev = prev;
        this.next = next;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
        this.total = total;
    }

    @NonNull
    public String getNext() {
        return next;
    }

    @NonNull
    public String getPrev() {
        return prev;
    }

    public boolean isHasPrev() {
        return hasPrev;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public int getTotal() {
        return total;
    }

    @NonNull
    public Cursor toDomain() {
        return new Cursor(next, hasNext);
    }
}
