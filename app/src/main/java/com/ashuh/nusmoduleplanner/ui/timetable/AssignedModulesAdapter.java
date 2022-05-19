package com.ashuh.nusmoduleplanner.ui.timetable;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.model.util.DateUtil;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class AssignedModulesAdapter
        extends RecyclerView.Adapter<AssignedModulesAdapter.ViewHolder> {

    private static final int MINUTES_PER_HOUR = 60;

    private final List<AssignedModule> assignedModules = new ArrayList<>();
    private final TimetableViewModel viewModel;

    public AssignedModulesAdapter(TimetableViewModel viewModel) {
        this.viewModel = viewModel;
    }

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

        AssignedModule assignedModule = assignedModules.get(position);

        String title = String.format(res.getString(R.string.module_card_title),
                assignedModule.getModuleCode(),
                assignedModule.getTitle());

        String examString = generateExamInfoText(assignedModule.getExamDate(),
                assignedModule.getExamDuration());
        String description =
                String.format(res.getString(R.string.module_card_description), examString,
                        assignedModule.getModuleCredit());

        viewHolder.getTitleTextView().setText(title);
        viewHolder.getDescriptionTextView().setText(description);
    }

    @Override
    public int getItemCount() {
        return assignedModules.size();
    }

    private String generateExamInfoText(ZonedDateTime examDate, int examDuration) {
        if (examDate == null) {
            return "No Exam";
        }

        String examDateString = examDate
                .withZoneSameInstant(ZoneId.systemDefault())
                .format(DateUtil.DATE_FORMATTER_DISPLAY);
        String examDurationString = (double) examDuration / MINUTES_PER_HOUR + " hrs";
        return examDateString + " " + examDurationString;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAssignedModules(List<AssignedModule> assignedModules) {
        this.assignedModules.clear();
        this.assignedModules.addAll(assignedModules);
        notifyDataSetChanged();
    }

    public void deleteModule(int id) {
        AssignedModule deleted = assignedModules.get(id);
        assignedModules.remove(id);
        viewModel.delete(deleted.getSemType(), deleted.getModuleCode());
        notifyItemChanged(id);
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
