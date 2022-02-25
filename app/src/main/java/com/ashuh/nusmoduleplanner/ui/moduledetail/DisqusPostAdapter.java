package com.ashuh.nusmoduleplanner.ui.moduledetail;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.data.disqus.Post;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DisqusPostAdapter extends RecyclerView.Adapter<DisqusPostAdapter.ViewHolder> {

    private static final List<ChronoUnit> AGE_UNITS = new ArrayList<>(
            Arrays.asList(ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS,
                    ChronoUnit.HOURS, ChronoUnit.MINUTES));
    private static final String AGE_TEXT = "%s %s ago";

    private List<Post> posts = new ArrayList<>();

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
        String ageText = getAgeText(post.getCreatedAt());

        StringJoiner titleJoiner = new StringJoiner(" • ");
        titleJoiner.add(author).add(ageText);

        viewHolder.getTitleTextView().setText(titleJoiner.toString());
        viewHolder.getMessageTextView().setText(post.getRawMessage());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    private String getAgeText(LocalDateTime postTime) {
        LocalDateTime now = LocalDateTime.now();

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
}
