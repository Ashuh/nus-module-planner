package com.ashuh.nusmoduleplanner.moduledetail.presentation.model;

import static java.util.Objects.requireNonNull;

import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

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
    private final Spanned name;
    @NonNull
    private final String age;
    @NonNull
    private final Spanned message;

    public UiPost(@NonNull Spanned name, @NonNull String age, @NonNull Spanned message) {
        requireNonNull(name);
        requireNonNull(age);
        requireNonNull(message);
        this.name = name;
        this.age = age;
        this.message = message;
    }

    public static UiPost fromDomain(@NonNull Post post, int primaryColor) {
        Spanned name = formatName(post.getAuthor().getName(), primaryColor);
        String age = calculateAge(post.getCreatedAt());
        Spanned message = Html.fromHtml(post.getMessage(), Html.FROM_HTML_MODE_LEGACY);
        return new UiPost(name, age, message);
    }

    private static Spanned formatName(String name, int color) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(name);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        stringBuilder.setSpan(colorSpan, 0, name.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        StyleSpan boldStyleSpan = new StyleSpan(Typeface.BOLD);
        stringBuilder.setSpan(boldStyleSpan, 0, name.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return stringBuilder;
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
    public Spanned getName() {
        return name;
    }

    @NonNull
    public String getAge() {
        return age;
    }

    @NonNull
    public Spanned getMessage() {
        return message;
    }
}
