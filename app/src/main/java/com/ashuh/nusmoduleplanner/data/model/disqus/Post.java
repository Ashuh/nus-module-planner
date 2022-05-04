package com.ashuh.nusmoduleplanner.data.model.disqus;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Post {

    private Author author;

    private LocalDateTime createdAt;

    private String message;

    public Author getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }


}
