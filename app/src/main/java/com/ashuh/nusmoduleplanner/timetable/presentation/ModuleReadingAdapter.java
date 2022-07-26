package com.ashuh.nusmoduleplanner.timetable.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.MainActivity;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiModuleReading;

import java.util.ArrayList;
import java.util.List;

public class ModuleReadingAdapter
        extends RecyclerView.Adapter<ModuleReadingAdapter.ViewHolder> {
    private static final String EXAM_INFO_FORMAT = "Exam: %s";
    private static final String TEXT_NO_EXAM = "No Exam";

    private final int semester;

    @NonNull
    private final List<UiModuleReading> moduleReadings = new ArrayList<>();

    public ModuleReadingAdapter(int semester) {
        this.semester = semester;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timetable_card_layout, parent, false);
        return new ModuleReadingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Context context = viewHolder.itemView.getContext();

        Resources res = context.getResources();
        UiModuleReading moduleReading = moduleReadings.get(position);

        Button button = viewHolder.colorButton;
        button.setBackgroundColor(moduleReading.getColor());
        button.setOnClickListener(v -> {
            String moduleCode = moduleReading.getModuleCode();
            DialogFragment dialog = new ColorSelectDialogFragment(moduleCode, semester);
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            dialog.show(fragmentManager, null);
        });

        String title = String.format(res.getString(R.string.module_card_title),
                moduleReading.getModuleCode(), moduleReading.getTitle());
        viewHolder.titleTextView.setText(title);

        String examDate = generateExamText(moduleReading.getExamDate());
        String description = String.format(res.getString(R.string.module_card_description),
                examDate, moduleReading.getModuleCredit());
        viewHolder.descriptionTextView.setText(description);
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

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button colorButton;
        private final TextView titleTextView;
        private final TextView descriptionTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            colorButton = itemView.findViewById(R.id.color_button);
            titleTextView = itemView.findViewById(R.id.module_card_title);
            descriptionTextView = itemView.findViewById(R.id.module_card_desc);
        }
    }
}
