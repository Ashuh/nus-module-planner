package com.ashuh.nusmoduleplanner.modulelist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.common.NusModulePlannerApplication;
import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;

public class ModuleListFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private ModuleListViewModel viewModel;
    private ModuleListAdapter adapter;

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
        viewModel.getModuleListObservable().observe(this, adapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_modules, container, false);

        searchView = rootView.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
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
}
