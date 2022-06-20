package com.ashuh.nusmoduleplanner.moduledetail;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.domain.model.post.Post;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisqusPostAdapter extends RecyclerView.Adapter<DisqusPostAdapter.ViewHolder> {

    private static final List<ChronoUnit> AGE_UNITS = new ArrayList<>(
            Arrays.asList(ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS,
                    ChronoUnit.HOURS, ChronoUnit.MINUTES));
    private static final String AGE_TEXT = "%s %s ago";

    private List<Post> posts = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.disqus_card_layout, parent, false);
        return new DisqusPostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Post post = posts.get(position);
        String author = post.getAuthor().getName();
        String age = getAgeText(post.getCreatedAt());

        String headingText = String.format("%s • %s", author, age);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(headingText);

        int color = viewHolder.getMessageTextView()
                .getContext()
                .getResources()
                .getColor(R.color.primary, viewHolder.getMessageTextView().getContext().getTheme());
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        stringBuilder.setSpan(colorSpan, 0, author.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        StyleSpan boldStyleSpan = new StyleSpan(Typeface.BOLD);
        stringBuilder.setSpan(boldStyleSpan, 0, author.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        viewHolder.getTitleTextView().setText(stringBuilder);
        Spanned spanned = Html.fromHtml(post.getMessage(), Html.FROM_HTML_MODE_LEGACY);
        viewHolder.getMessageTextView().setText(spanned);
        viewHolder.getMessageTextView().setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private String getAgeText(ZonedDateTime postTime) {
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

    @SuppressLint("NotifyDataSetChanged")
    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userTextView;
        private final TextView messageTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userTextView = itemView.findViewById(R.id.disqus_card_title);
            messageTextView = itemView.findViewById(R.id.disqus_card_message);
        }

        public TextView getTitleTextView() {
            return userTextView;
        }

        public TextView getMessageTextView() {
            return messageTextView;
        }
    }
}
