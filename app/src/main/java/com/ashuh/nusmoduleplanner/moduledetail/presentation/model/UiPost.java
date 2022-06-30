package com.ashuh.nusmoduleplanner.moduledetail.presentation.model;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

public class UiPost {
    @NonNull
    private final String name;
    @NonNull
    private final String age;
    @NonNull
    private final String message;

    public UiPost(@NonNull String name, @NonNull String age, @NonNull String message) {
        requireNonNull(name);
        requireNonNull(age);
        requireNonNull(message);
        this.name = name;
        this.age = age;
        this.message = message;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getAge() {
        return age;
    }

    @NonNull
    public String getMessage() {
        return message;
    }
}
