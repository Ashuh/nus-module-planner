package com.ashuh.nusmoduleplanner.moduledetail.presentation;

import android.annotation.SuppressLint;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiPost;

import java.util.ArrayList;
import java.util.List;

public class DisqusPostAdapter extends RecyclerView.Adapter<DisqusPostAdapter.ViewHolder> {
    private List<UiPost> posts = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.disqus_card_layout, parent, false);
        return new DisqusPostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        UiPost post = posts.get(position);
        viewHolder.setTitle(buildTitle(post.getName(), post.getAge()));
        viewHolder.setMessage(post.getMessage());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private Spanned buildTitle(CharSequence name, CharSequence age) {
        SpannableStringBuilder titleBuilder = new SpannableStringBuilder();
        titleBuilder.append(name)
                .append(" • ")
                .append(age);
        return titleBuilder;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPosts(List<UiPost> posts) {
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
            messageTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        public void setTitle(CharSequence title) {
            userTextView.setText(title);
        }

        public void setMessage(CharSequence message) {
            messageTextView.setText(message);
        }
    }
}
