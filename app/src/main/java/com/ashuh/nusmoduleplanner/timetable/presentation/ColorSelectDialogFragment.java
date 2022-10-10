package com.ashuh.nusmoduleplanner.timetable.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.AppContainer;
import com.ashuh.nusmoduleplanner.common.NusModulePlannerApplication;
import com.ashuh.nusmoduleplanner.common.data.preferences.SharedPreferencesManager;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;

import java.util.ArrayList;
import java.util.List;

public class ColorSelectDialogFragment extends DialogFragment
        implements Observer<List<Integer>>, AdapterView.OnItemClickListener {
    @NonNull
    private final String moduleCode;
    private final int semester;
    private ColorSelectViewModel viewModel;
    private ColorAdapter adapter;

    public ColorSelectDialogFragment(@NonNull String moduleCode, int semester) {
        this.semester = semester;
        this.moduleCode = moduleCode;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContainer container = ((NusModulePlannerApplication) requireActivity().getApplication())
                .appContainer;
        ModuleRepository moduleRepository = container.moduleRepository;
        SharedPreferencesManager sharedPreferencesManager = container.sharedPreferencesManager;
        viewModel = new ViewModelProvider(this,
                new ColorSelectViewModelFactory(moduleRepository, sharedPreferencesManager))
                .get(ColorSelectViewModel.class);
        adapter = new ColorAdapter(requireContext(), R.layout.color_select_item, new ArrayList<>());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.color_select_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ListView listView = view.findViewById(R.id.list);
        viewModel.getSelectedColorSchemeColors().observe(this, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onChanged(List<Integer> integers) {
        adapter.clear();
        adapter.addAll(integers);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        viewModel.updateColor(moduleCode, semester, position);
        dismiss();
    }
}
