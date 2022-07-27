package com.ashuh.nusmoduleplanner.timetable.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashuh.nusmoduleplanner.R;

import java.util.List;

public class ColorAdapter extends ArrayAdapter<Integer> {

    public ColorAdapter(@NonNull Context context, int resource, @NonNull List<Integer> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.color_select_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int color = getItem(position);
        viewHolder.blob.setBackgroundColor(color);
        return convertView;
    }

    private static class ViewHolder {
        private final View blob;

        ViewHolder(View view) {
            blob = view.findViewById(R.id.blob);
        }
    }
}
