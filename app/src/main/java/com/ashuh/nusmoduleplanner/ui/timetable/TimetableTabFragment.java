package com.ashuh.nusmoduleplanner.ui.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;

public class TimetableTabFragment extends Fragment {
    public static final String ARG_SEMESTER = "semester";
    private SemesterType semType;
    private TimetableViewModel viewModel;
    private AssignedModulesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        assert args != null;
        semType = SemesterType.fromId(args.getInt(ARG_SEMESTER));
        viewModel = new ViewModelProvider(this, new TimetableViewModelFactory(semType))
                .get(TimetableViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timetable_tab, container, false);
        adapter = new AssignedModulesAdapter();
        RecyclerView recyclerView = rootView.findViewById(R.id.timetable_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TimetableView timetableView = view.findViewById(R.id.revolving_weekview);
        timetableView.setSemType(semType);

        viewModel.getTimetableEntriesObservable().observe(getViewLifecycleOwner(), entries -> {
            if (entries == null) {
                return;
            }
            adapter.setAssignedModules(entries);
            timetableView.setAssignedModules(entries);
        });
    }
}
