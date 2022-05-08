package com.ashuh.nusmoduleplanner.ui.modules;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;

public class ModuleListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private ModuleListViewModel viewModel;
    private ModuleListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_modules, container, false);
        searchView = rootView.findViewById(R.id.search_view);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ModuleListAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ModuleListViewModel.class);
        observeViewModel();
        setSearchListener();

        return rootView;
    }

    private void observeViewModel() {
        viewModel.getModuleListObservable().observe(getViewLifecycleOwner(), modules -> {
            if (modules == null) {
                return;
            }
            adapter.setModules(modules);
        });
    }

    private void setSearchListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return true;
            }
        });
    }
}
