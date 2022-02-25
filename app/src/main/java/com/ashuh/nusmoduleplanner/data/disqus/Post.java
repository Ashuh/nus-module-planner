package com.ashuh.nusmoduleplanner.data.disqus;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Post {

    @SerializedName("author")
    private Author author;

    @SerializedName("createdAt")
    private LocalDateTime createdAt;

    @SerializedName("raw_message")
    private String rawMessage;

    public Author getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getRawMessage() {
        return rawMessage;
    }


}
