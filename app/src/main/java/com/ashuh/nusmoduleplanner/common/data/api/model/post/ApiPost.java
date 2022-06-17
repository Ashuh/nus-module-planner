package com.ashuh.nusmoduleplanner.common.data.api.model.post;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.post.Author;
import com.ashuh.nusmoduleplanner.common.domain.model.post.Post;
import com.google.gson.annotations.SerializedName;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ApiPost {
    private static final DateTimeFormatter DISQUS_DATE_TIME_FORMATTER
            = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of("UTC"));

    @NonNull
    @SerializedName("forum")
    private final String forum;
    @NonNull
    @SerializedName("thread")
    private final String thread;
    @NonNull
    @SerializedName("createdAt")
    private final String createdAt;
    @NonNull
    @SerializedName("message")
    private final String message;
    @NonNull
    @SerializedName("raw_message")
    private final String rawMessage;
    @NonNull
    @SerializedName("editableUntil")
    private final String editableUntil;
    @NonNull
    @SerializedName("author")
    private final ApiAuthor author;
    @NonNull
    @SerializedName("id")
    private final String id;
    @SerializedName("points")
    private final int points;
    @SerializedName("likes")
    private final int likes;
    @SerializedName("dislikes")
    private final int dislikes;
    @SerializedName("numReports")
    private final int numReports;
    @SerializedName("isSpam")
    private final boolean isSpam;
    @SerializedName("isHighlighted")
    private final boolean isHighlighted;
    @SerializedName("isDeletedByAuthor")
    private final boolean isDeletedByAuthor;
    @SerializedName("isDeleted")
    private final boolean isDeleted;
    @SerializedName("isApproved")
    private final boolean isApproved;
    @SerializedName("isFlagged")
    private final boolean isFlagged;
    @SerializedName("isEdited")
    private final boolean isEdited;
    @SerializedName("canVote")
    private final boolean canVote;

    public ApiPost(@NonNull String forum, @NonNull String thread, @NonNull String createdAt,
                   @NonNull String message, @NonNull String rawMessage,
                   @NonNull String editableUntil, @NonNull ApiAuthor author, @NonNull String id,
                   int points, int likes, int dislikes, int numReports, boolean isSpam,
                   boolean isHighlighted, boolean isDeletedByAuthor, boolean isDeleted,
                   boolean isApproved, boolean isFlagged, boolean isEdited, boolean canVote) {
        requireNonNull(forum);
        requireNonNull(thread);
        requireNonNull(createdAt);
        requireNonNull(message);
        requireNonNull(rawMessage);
        requireNonNull(editableUntil);
        requireNonNull(author);
        requireNonNull(id);
        this.forum = forum;
        this.thread = thread;
        this.createdAt = createdAt;
        this.message = message;
        this.rawMessage = rawMessage;
        this.editableUntil = editableUntil;
        this.author = author;
        this.id = id;
        this.points = points;
        this.likes = likes;
        this.dislikes = dislikes;
        this.numReports = numReports;
        this.isSpam = isSpam;
        this.isHighlighted = isHighlighted;
        this.isDeletedByAuthor = isDeletedByAuthor;
        this.isDeleted = isDeleted;
        this.isApproved = isApproved;
        this.isFlagged = isFlagged;
        this.isEdited = isEdited;
        this.canVote = canVote;
    }

    @NonNull
    public String getForum() {
        return forum;
    }

    @NonNull
    public String getThread() {
        return thread;
    }

    @NonNull
    public String getCreatedAt() {
        return createdAt;
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
    public String getEditableUntil() {
        return editableUntil;
    }

    @NonNull
    public ApiAuthor getAuthor() {
        return author;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public int getNumReports() {
        return numReports;
    }

    public boolean isSpam() {
        return isSpam;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public boolean isDeletedByAuthor() {
        return isDeletedByAuthor;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public boolean isCanVote() {
        return canVote;
    }

    @NonNull
    public Post toDomain() {
        Author domainAuthor = author.toDomain();
        ZonedDateTime domainCreatedAt = ZonedDateTime.parse(createdAt, DISQUS_DATE_TIME_FORMATTER);
        return new Post(id, dislikes, likes, points, numReports, isHighlighted, isSpam, isEdited,
                isDeleted, message, rawMessage, domainAuthor, domainCreatedAt);
    }
}
