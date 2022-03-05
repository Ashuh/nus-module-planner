package com.ashuh.nusmoduleplanner.ui.modules;

import android.content.res.Resources;
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
import com.ashuh.nusmoduleplanner.data.model.nusmods.ModuleCondensed;

import java.util.ArrayList;
import java.util.List;

public class ModuleListAdapter extends RecyclerView.Adapter<ModuleListAdapter.ViewHolder> implements Filterable {
    private final List<ModuleCondensed> modules = new ArrayList<>();
    private List<ModuleCondensed> filteredModules = new ArrayList<>();

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
        Resources res = viewHolder.titleTextView.getContext().getResources();
        ModuleCondensed module = filteredModules.get(position);

        String title =
                String.format(res.getString(R.string.module_card_title), module.getModuleCode(),
                        module.getTitle());

        String desc = String.format(res.getString(R.string.module_card_description),
                module.getDepartment(),
                module.getCredit());

        viewHolder.getTitleTextView().setText(title);
        viewHolder.getDescriptionTextView().setText(desc);

        viewHolder.itemView.setOnClickListener(view -> {
            ModuleListFragmentDirections.ActionNavModulesToNavModuleDetail action =
                    ModuleListFragmentDirections
                            .actionNavModulesToNavModuleDetail(module.getModuleCode());
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return filteredModules == null ? 0 : filteredModules.size();
    }

    public void setModules(List<ModuleCondensed> m) {
        this.modules.clear();
        this.modules.addAll(m);
        filteredModules.clear();
        filteredModules.addAll(m);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                List<ModuleCondensed> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList = modules;
                } else {
                    for (ModuleCondensed m : modules) {
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
                filteredModules = (ArrayList<ModuleCondensed>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView descriptionTextView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.module_card_title);
            descriptionTextView = view.findViewById(R.id.module_card_desc);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getDescriptionTextView() {
            return descriptionTextView;
        }
    }
}
