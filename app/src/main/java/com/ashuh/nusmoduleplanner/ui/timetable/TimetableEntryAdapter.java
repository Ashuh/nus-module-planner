package com.ashuh.nusmoduleplanner.ui.timetable;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.ModuleSemesterDatum;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.source.timetable.TimetableDataSource;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TimetableEntryAdapter extends RecyclerView.Adapter<TimetableEntryAdapter.ViewHolder> {

    private final List<AssignedModule> assignedModules = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timetable_card_layout, parent, false);
        return new TimetableEntryAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Resources res = viewHolder.titleTextView.getContext().getResources();

        AssignedModule assignedModule = assignedModules.get(position);
        ModuleSemesterDatum semData = assignedModule.getSemesterDatum();

        String title = String.format(res.getString(R.string.module_card_title),
                assignedModule.getModuleCode(),
                assignedModule.getTitle());

        String examString = semData.getExamInfo();
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

    @SuppressLint("NotifyDataSetChanged")
    public void setAssignedModules(List<AssignedModule> assignedModules) {
        this.assignedModules.clear();
        this.assignedModules.addAll(assignedModules);
        notifyDataSetChanged();
    }

    public void deleteModule(int id) {
        AssignedModule deleted = assignedModules.get(id);
        assignedModules.remove(id);
        TimetableDataSource.getInstance()
                .delete(deleted.getSemType(), deleted.getModuleCode());
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
