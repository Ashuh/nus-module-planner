package com.ashuh.nusmoduleplanner.common.domain.model.post;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

public class Cursor {
    @NonNull
    private final String next;
    private final boolean hasNext;

    public Cursor(@NonNull String next, boolean hasNext) {
        this.next = requireNonNull(next);
        this.hasNext = hasNext;
    }

    @NonNull
    public String getNext() {
        return next;
    }

    public boolean isHasNext() {
        return hasNext;
    }
}
