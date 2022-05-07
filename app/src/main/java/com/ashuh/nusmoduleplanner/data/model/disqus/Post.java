package com.ashuh.nusmoduleplanner.data.model.disqus;

import java.time.ZonedDateTime;

public class Post {

    private Author author;

    private ZonedDateTime createdAt;

    private String message;

    public Author getAuthor() {
        return author;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }


}
