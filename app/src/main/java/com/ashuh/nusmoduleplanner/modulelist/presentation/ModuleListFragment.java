package com.ashuh.nusmoduleplanner.modulelist.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.NusModulePlannerApplication;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class ModuleListFragment extends Fragment implements SearchView.OnQueryTextListener,
        Observer<ModuleListState> {
    private CircularProgressIndicator progressIndicator;
    private ModuleListAdapter adapter;
    private ModuleListViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ModuleListAdapter();
        ModuleRepository moduleRepository
                = ((NusModulePlannerApplication) requireActivity().getApplication())
                .appContainer.moduleRepository;
        viewModel = new ViewModelProvider(this,
                new ModuleListViewModelFactory(moduleRepository))
                .get(ModuleListViewModel.class);
        viewModel.getState().observe(this, this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modules, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressIndicator = view.findViewById(R.id.progress_indicator);

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        viewModel.filter(query);
        return true;
    }

    @Override
    public void onChanged(ModuleListState state) {
        if (state.isLoading()) {
            progressIndicator.show();
        } else {
            progressIndicator.hide();
            adapter.setModules(state.getModules());
        }
    }
}
