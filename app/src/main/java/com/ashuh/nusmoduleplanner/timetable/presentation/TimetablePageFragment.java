package com.ashuh.nusmoduleplanner.timetable.presentation;

import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.ashuh.nusmoduleplanner.common.data.preferences.SharedPreferencesManager;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.module.lesson.LessonType;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiModuleReading;
import com.ashuh.nusmoduleplanner.timetable.presentation.model.UiTimetableLessonOccurrence;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class TimetablePageFragment extends Fragment implements WeekView.EventClickListener,
        Observer<TimetableState> {
    public static final String ARG_SEMESTER = "semester";

    private ConstraintLayout timetablePageContainer;
    private CircularProgressIndicator progressIndicator;
    private TimetableView timetableView;
    private TimetableViewModel viewModel;
    private ModuleReadingAdapter adapter;
    private TimetableLoader loader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        assert args != null;

        int semInt = args.getInt(ARG_SEMESTER);
        Semester semester = Semester.fromInt(semInt);
        ModuleRepository moduleRepository
                = ((NusModulePlannerApplication) requireActivity().getApplication())
                .appContainer.moduleRepository;
        SharedPreferencesManager preferenceRepository
                = ((NusModulePlannerApplication) requireActivity().getApplication())
                .appContainer.sharedPreferencesManager;
        viewModel = new ViewModelProvider(this,
                new TimetableViewModelFactory(moduleRepository, preferenceRepository, semester))
                .get(TimetableViewModel.class);
        viewModel.getState().observe(this, this);
        adapter = new ModuleReadingAdapter(semInt);
        loader = new TimetableLoader();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timetable_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        timetablePageContainer = view.findViewById(R.id.timetable_page_container);
        progressIndicator = view.findViewById(R.id.progress_indicator);
        timetableView = view.findViewById(R.id.revolving_weekview);
        timetableView.setOnEventClickListener(this);
        timetableView.setWeekViewLoader(loader);
        RecyclerView recyclerView = view.findViewById(R.id.timetable_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        UiTimetableLessonOccurrence lessonOccurrence = (UiTimetableLessonOccurrence) event;
        String moduleCode = lessonOccurrence.getModuleCode();
        LessonType lessonType = lessonOccurrence.getLessonType();
        viewModel.getAlternateLessons(moduleCode, lessonType, lessonOccurrence.getLessonNo())
                .observe(getViewLifecycleOwner(), altLessons -> {
                    if (altLessons == null || altLessons.size() <= 1) {
                        return;
                    }
                    DialogFragment dialog = new LessonSelectDialogFragment(altLessons);
                    FragmentManager fragmentManager
                            = ((MainActivity) requireContext()).getSupportFragmentManager();
                    dialog.show(fragmentManager, "lessons");
//                    viewModel.getLessons(moduleCode, semester, lessonType)
//                            .removeObservers(this);
                });
    }

    @Override
    public void onChanged(TimetableState state) {
        if (state == null) {
            return;
        }

        if (state.isLoading()) {
            timetablePageContainer.setVisibility(View.INVISIBLE);
            progressIndicator.show();
            return;
        }

        timetablePageContainer.setVisibility(View.VISIBLE);
        progressIndicator.hide();
        adapter.setModuleReadings(state.getModuleReadings());
        loader.setOccurrences(state.getLessonOccurrences());
        timetableView.notifyDatasetChanged();
    }

    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private final ModuleReadingAdapter adapter;

        public SwipeToDeleteCallback(ModuleReadingAdapter adapter) {
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
            UiModuleReading deletedReading = adapter.getModule(position);
            String moduleCode = deletedReading.getModuleCode();
            viewModel.deleteModuleReading(moduleCode);
        }
    }
}
