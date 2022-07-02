package com.ashuh.nusmoduleplanner.modulelist.presentation;

import static java.util.Objects.requireNonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.modulelist.presentation.model.UiModuleInfo;

import java.util.ArrayList;
import java.util.List;

public class ModuleListAdapter extends RecyclerView.Adapter<ModuleListAdapter.ViewHolder> {
    @NonNull
    private List<UiModuleInfo> modules = new ArrayList<>();

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
        UiModuleInfo module = modules.get(position);
        viewHolder.setModule(module);
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    public void setModules(@NonNull List<UiModuleInfo> modules) {
        this.modules = requireNonNull(modules);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TEXT_TITLE = "%s %s";
        private static final String TEXT_ADMIN_INFO = "%s • %s";

        private final TextView titleTextView;
        private final TextView descriptionTextView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.module_card_title);
            descriptionTextView = view.findViewById(R.id.module_card_desc);
        }

        public void setModule(@NonNull UiModuleInfo module) {
            requireNonNull(module);
            String title = String.format(TEXT_TITLE, module.getModuleCode(), module.getTitle());
            String desc = String
                    .format(TEXT_ADMIN_INFO, module.getDepartment(), module.getModuleCredit());

            titleTextView.setText(title);
            descriptionTextView.setText(desc);

            itemView.setOnClickListener(view -> {
                ModuleListFragmentDirections.ActionNavModulesToNavModuleDetail action
                        = ModuleListFragmentDirections.actionNavModulesToNavModuleDetail(
                        module.getModuleCode());
                Navigation.findNavController(view).navigate(action);
            });
        }
    }
}
