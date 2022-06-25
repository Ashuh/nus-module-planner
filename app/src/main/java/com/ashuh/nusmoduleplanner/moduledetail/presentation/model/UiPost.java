package com.ashuh.nusmoduleplanner.moduledetail.presentation.model;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.post.Post;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class UiPost {
    private static final List<ChronoUnit> AGE_UNITS = List.of(ChronoUnit.YEARS, ChronoUnit.MONTHS,
            ChronoUnit.DAYS, ChronoUnit.HOURS, ChronoUnit.MINUTES);
    private static final String AGE_TEXT = "%s %s ago";

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

    public static UiPost fromDomain(@NonNull Post post) {
        String name = post.getAuthor().getName();
        String age = calculateAge(post.getCreatedAt());
        String message = post.getMessage();
        return new UiPost(name, age, message);
    }

    private static String calculateAge(ZonedDateTime postTime) {
        ZonedDateTime now = ZonedDateTime.now();

        long age = 0;
        ChronoUnit ageUnit = AGE_UNITS.get(AGE_UNITS.size() - 1);

        for (ChronoUnit unit : AGE_UNITS) {
            age = unit.between(postTime, now);
            if (age > 0) {
                ageUnit = unit;
                break;
            }
        }

        return String.format(AGE_TEXT, age, ageUnit);
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
