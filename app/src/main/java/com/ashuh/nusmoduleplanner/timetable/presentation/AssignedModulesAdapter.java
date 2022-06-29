package com.ashuh.nusmoduleplanner.timetable.presentation;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiModuleReading;

import java.util.ArrayList;
import java.util.List;


public class AssignedModulesAdapter
        extends RecyclerView.Adapter<AssignedModulesAdapter.ViewHolder> {
    private static final String EXAM_INFO_FORMAT = "Exam: %s";
    private static final String TEXT_NO_EXAM = "No Exam";

    @NonNull
    private final List<UiModuleReading> moduleReadings = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timetable_card_layout, parent, false);
        return new AssignedModulesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Resources res = viewHolder.titleTextView.getContext().getResources();
        UiModuleReading moduleReading = moduleReadings.get(position);

        String title = String.format(res.getString(R.string.module_card_title),
                moduleReading.getModuleCode(), moduleReading.getTitle());
        viewHolder.getTitleTextView().setText(title);

        String examDate = generateExamText(moduleReading.getExamDate());
        String description = String.format(res.getString(R.string.module_card_description),
                examDate, moduleReading.getModuleCredit());
        viewHolder.getDescriptionTextView().setText(description);
    }

    @Override
    public int getItemCount() {
        return moduleReadings.size();
    }

    private String generateExamText(@NonNull String examDate) {
        if (examDate.isEmpty()) {
            return TEXT_NO_EXAM;
        }
        return String.format(EXAM_INFO_FORMAT, examDate);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setModuleReadings(List<UiModuleReading> moduleReadings) {
        this.moduleReadings.clear();
        this.moduleReadings.addAll(moduleReadings);
        notifyDataSetChanged();
    }

    public UiModuleReading getModule(int position) {
        return moduleReadings.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.module_card_title);
            descriptionTextView = itemView.findViewById(R.id.module_card_desc);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getDescriptionTextView() {
            return descriptionTextView;
        }
    }
}
