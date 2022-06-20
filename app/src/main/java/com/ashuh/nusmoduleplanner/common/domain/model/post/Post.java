package com.ashuh.nusmoduleplanner.common.domain.model.post;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.time.ZonedDateTime;

public class Post {
    private final String id;
    private final int dislikes;
    private final int likes;
    private final int points;
    private final int numReports;
    private final boolean isHighlighted;
    private final boolean isSpam;
    private final boolean isEdited;
    private final boolean isDeleted;
    @NonNull
    private final String message;
    @NonNull
    private final String rawMessage;
    @NonNull
    private final Author author;
    @NonNull
    private final ZonedDateTime createdAt;

    public Post(String id, int dislikes, int likes, int points, int numReports,
                boolean isHighlighted, boolean isSpam, boolean isEdited, boolean isDeleted,
                @NonNull String message, @NonNull String rawMessage, @NonNull Author author,
                @NonNull ZonedDateTime createdAt) {
        requireNonNull(message);
        requireNonNull(rawMessage);
        requireNonNull(author);
        requireNonNull(createdAt);
        this.id = id;
        this.dislikes = dislikes;
        this.likes = likes;
        this.points = points;
        this.numReports = numReports;
        this.isHighlighted = isHighlighted;
        this.isSpam = isSpam;
        this.isEdited = isEdited;
        this.isDeleted = isDeleted;
        this.message = message;
        this.rawMessage = rawMessage;
        this.author = author;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public int getDislikes() {
        return dislikes;
    }

    public int getLikes() {
        return likes;
    }

    public int getPoints() {
        return points;
    }

    public int getNumReports() {
        return numReports;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public boolean isSpam() {
        return isSpam;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @NonNull
    public String getRawMessage() {
        return rawMessage;
    }

    @NonNull
    public Author getAuthor() {
        return author;
    }

    @NonNull
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
