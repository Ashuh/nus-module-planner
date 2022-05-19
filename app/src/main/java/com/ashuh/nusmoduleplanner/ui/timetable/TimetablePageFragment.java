package com.ashuh.nusmoduleplanner.ui.timetable;

import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.MainActivity;
import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.model.timetable.TimetableEvent;
import com.ashuh.nusmoduleplanner.data.source.timetable.TimetableDAO;
import com.ashuh.nusmoduleplanner.data.source.timetable.TimetableDataSource;
import com.ashuh.nusmoduleplanner.data.source.timetable.TimetableDatabase;

import java.util.List;

import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class TimetablePageFragment extends Fragment implements WeekView.EventClickListener,
        Observer<List<AssignedModule>> {

    public static final String ARG_SEMESTER = "semester";

    private TimetableView timetableView;
    private SemesterType semType;
    private TimetableViewModel viewModel;
    private AssignedModulesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        assert args != null;
        semType = SemesterType.fromId(args.getInt(ARG_SEMESTER));

        TimetableDAO dao = TimetableDatabase.getInstance(getContext()).dao();
        TimetableDataSource dataSource = new TimetableDataSource(dao);

        viewModel = new ViewModelProvider(this, new TimetableViewModelFactory(dataSource, semType))
                .get(TimetableViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timetable_tab, container, false);
        adapter = new AssignedModulesAdapter(viewModel);
        RecyclerView recyclerView = rootView.findViewById(R.id.timetable_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        timetableView = view.findViewById(R.id.revolving_weekview);
        timetableView.setSemType(semType);
        timetableView.setOnEventClickListener(this);
        viewModel.getTimetableEntriesObservable().observe(getViewLifecycleOwner(), this);
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        TimetableEvent ttEvent = (TimetableEvent) event;

        if (ttEvent.getAlternateLessonCodes().isEmpty()) {
            return;
        }

        DialogFragment fragment = new LessonSelectDialogFragment(ttEvent, viewModel);
        FragmentManager fragmentManager
                = ((MainActivity) requireContext()).getSupportFragmentManager();
        fragment.show(fragmentManager, "lessons");
    }

    @Override
    public void onChanged(List<AssignedModule> assignedModules) {
        if (assignedModules == null) {
            return;
        }

        adapter.setAssignedModules(assignedModules);
        timetableView.setAssignedModules(assignedModules);
    }

    public static class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private final AssignedModulesAdapter adapter;

        public SwipeToDeleteCallback(AssignedModulesAdapter adapter) {
            super(ItemTouchHelper.RIGHT, ItemTouchHelper.RIGHT);
            this.adapter = adapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            adapter.deleteModule(position);
        }
    }
}
