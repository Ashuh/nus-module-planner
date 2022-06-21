package com.ashuh.nusmoduleplanner.modulelist;

import static com.ashuh.nusmoduleplanner.modulelist.ModuleListFragmentDirections.actionNavModulesToNavModuleDetail;
import static java.util.Objects.requireNonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleInfo;

import java.util.ArrayList;
import java.util.List;

public class ModuleListAdapter extends RecyclerView.Adapter<ModuleListAdapter.ViewHolder>
        implements Observer<List<ModuleInfo>> {
    @NonNull
    private final List<ModuleInfo> modules = new ArrayList<>();

    @NonNull
    @Override
    public ModuleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                           int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.module_card_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleListAdapter.ViewHolder viewHolder, int position) {
        ModuleInfo module = modules.get(position);
        viewHolder.setModule(module);
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    @Override
    public void onChanged(List<ModuleInfo> modules) {
        if (modules == null) {
            return;
        }
        this.modules.clear();
        this.modules.addAll(modules);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TEXT_TITLE = "%s %s";
        private static final String TEXT_ADMIN_INFO = "%s • %s MCs";

        private final TextView titleTextView;
        private final TextView descriptionTextView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.module_card_title);
            descriptionTextView = view.findViewById(R.id.module_card_desc);
        }

        public void setModule(@NonNull ModuleInfo module) {
            requireNonNull(module);
            String title = String.format(TEXT_TITLE, module.getModuleCode(), module.getTitle());
            String desc = String
                    .format(TEXT_ADMIN_INFO, module.getDepartment(), module.getModuleCredit());

            titleTextView.setText(title);
            descriptionTextView.setText(desc);

            itemView.setOnClickListener(view -> {
                ModuleListFragmentDirections.ActionNavModulesToNavModuleDetail action
                        = actionNavModulesToNavModuleDetail(module.getModuleCode());
                Navigation.findNavController(view).navigate(action);
            });
        }
    }
}
