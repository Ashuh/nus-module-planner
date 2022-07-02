package com.ashuh.nusmoduleplanner.moduledetail.presentation;

import static java.util.Objects.requireNonNull;

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
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiPost;

import java.util.ArrayList;
import java.util.List;

public class DisqusPostAdapter extends RecyclerView.Adapter<DisqusPostAdapter.ViewHolder> {
    @NonNull
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
        int color = viewHolder.itemView.getContext()
                .getResources()
                .getColor(R.color.primary, viewHolder.itemView.getContext().getTheme());
        Spanned formattedName = formatName(post.getName(), color);
        Spanned title = buildTitle(formattedName, post.getAge());
        Spanned message = Html.fromHtml(post.getMessage(), Html.FROM_HTML_MODE_LEGACY);
        viewHolder.setTitle(title);
        viewHolder.setMessage(message);
    }

    @Override
    public int getItemCount() {
        return posts.size();
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

    private Spanned buildTitle(CharSequence name, CharSequence age) {
        SpannableStringBuilder titleBuilder = new SpannableStringBuilder();
        titleBuilder.append(name)
                .append(" • ")
                .append(age);
        return titleBuilder;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPosts(@NonNull List<UiPost> posts) {
        this.posts = requireNonNull(posts);
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
