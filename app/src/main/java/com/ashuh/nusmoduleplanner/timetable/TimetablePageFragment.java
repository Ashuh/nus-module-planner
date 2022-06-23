package com.ashuh.nusmoduleplanner.timetable;

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

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.MainActivity;
import com.ashuh.nusmoduleplanner.common.NusModulePlannerApplication;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.Lesson;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;

import java.util.List;
import java.util.stream.Collectors;

import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class TimetablePageFragment extends Fragment implements WeekView.EventClickListener,
        Observer<List<ModuleReading>> {
    public static final String ARG_SEMESTER = "semester";

    private TimetableView timetableView;
    private Semester semester;
    private TimetableViewModel viewModel;
    private AssignedModulesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        assert args != null;

        int semInt = args.getInt(ARG_SEMESTER);
        semester = Semester.fromInt(semInt);

        ModuleRepository moduleRepository
                = ((NusModulePlannerApplication) requireActivity().getApplication())
                .appContainer.moduleRepository;

        viewModel = new ViewModelProvider(this,
                new TimetableViewModelFactory(moduleRepository, semester))
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
        timetableView.setOnEventClickListener(this);
        viewModel.getTimetableEntriesObservable().observe(getViewLifecycleOwner(), this);
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        TimetableEvent ttEvent = (TimetableEvent) event;
        String moduleCode = ttEvent.getModuleCode();
        LessonType lessonType = ttEvent.getLessonType();
        viewModel.getLessons(moduleCode, semester, lessonType)
                .observe(getViewLifecycleOwner(), lessons -> {
                    if (lessons == null || lessons.size() <= 1) {
                        return;
                    }
                    List<Lesson> altLessons = lessons.stream()
                            .filter(lesson -> !lesson.getLessonNo().equals(ttEvent.getLessonNo()))
                            .collect(Collectors.toList());
                    DialogFragment dialog = new LessonSelectDialogFragment(moduleCode, semester,
                            lessonType, altLessons, viewModel);
                    FragmentManager fragmentManager
                            = ((MainActivity) requireContext()).getSupportFragmentManager();
                    dialog.show(fragmentManager, "lessons");
//                    viewModel.getLessons(moduleCode, semester, lessonType)
//                            .removeObservers(this);
                });
    }

    @Override
    public void onChanged(List<ModuleReading> entries) {
        if (entries == null) {
            return;
        }
        adapter.setAssignedModules(entries);
        timetableView.setAssignedModules(entries);
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