package com.ashuh.nusmoduleplanner.ui.modules;

import static com.ashuh.nusmoduleplanner.ui.modules.ModuleListFragmentDirections.actionNavModulesToNavModuleDetail;
import static java.util.Objects.requireNonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.ModuleInformation;

import java.util.ArrayList;
import java.util.List;

public class ModuleListAdapter extends RecyclerView.Adapter<ModuleListAdapter.ViewHolder>
        implements Filterable {

    @NonNull
    private final List<ModuleInformation> modules = new ArrayList<>();
    @NonNull
    private List<ModuleInformation> filteredModules = new ArrayList<>();

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
        ModuleInformation module = filteredModules.get(position);
        viewHolder.setModule(module);
    }

    @Override
    public int getItemCount() {
        return filteredModules.size();
    }

    public void setModules(List<ModuleInformation> modules) {
        if (modules == null) {
            return;
        }
        this.modules.clear();
        this.modules.addAll(modules);
        filteredModules.clear();
        filteredModules.addAll(modules);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                List<ModuleInformation> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList = modules;
                } else {
                    for (ModuleInformation m : modules) {
                        if (m.getModuleCode().contains(charString.toUpperCase())) {
                            filteredList.add(m);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredModules = (ArrayList<ModuleInformation>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static final String TEXT_TITLE = "%s %s MCs";
        private static final String TEXT_ADMIN_INFO = "%s • %s MCs";

        private final TextView titleTextView;
        private final TextView descriptionTextView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.module_card_title);
            descriptionTextView = view.findViewById(R.id.module_card_desc);
        }

        public void setModule(@NonNull ModuleInformation module) {
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
