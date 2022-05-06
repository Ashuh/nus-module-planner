package com.ashuh.nusmoduleplanner.ui.moduledetail;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.MainActivity;
import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.data.model.nusmods.AcademicYear;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.ModuleInformationSemesterDatum;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.ModuleSemesterDatum;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.util.DateUtil;
import com.ashuh.nusmoduleplanner.data.source.disqus.DisqusRepository;
import com.ashuh.nusmoduleplanner.data.source.nusmods.ModulesRepository;
import com.ashuh.nusmoduleplanner.data.source.timetable.TimetableDataSource;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModuleDetailFragment extends Fragment {

    private static final int MINUTES_PER_HOUR = 60;

    private ModuleDetailViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Module Details");
        View root = inflater.inflate(R.layout.fragment_module_detail, container, false);

        String moduleCode = ModuleDetailFragmentArgs.fromBundle(getArguments()).getModuleCode();
        viewModel = new ViewModelProvider(this,
                new ModuleDetailViewModelFactory(AcademicYear.getCurrent(), moduleCode))
                .get(ModuleDetailViewModel.class);
        observeViewModel(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void observeViewModel(View root) {
        final TextView titleTextView = root.findViewById(R.id.module_title);
        final TextView codeTextView = root.findViewById(R.id.module_code);
        final TextView infoTextView = root.findViewById(R.id.module_info);
        final TextView semestersTextView = root.findViewById(R.id.module_semesters);
        final TextView descriptionTextView = root.findViewById(R.id.module_description);
        final TextView prerequisiteTextView = root.findViewById(R.id.module_prerequisite);
        final TextView corequisiteTextView = root.findViewById(R.id.module_corequisite);
        final TextView preclusionTextView = root.findViewById(R.id.module_preclusion);
        final TextView prerequisiteHeaderTextView = root
                .findViewById(R.id.module_prerequisite_header);
        final TextView corequisiteHeaderTextView = root
                .findViewById(R.id.module_corequisite_header);
        final TextView preclusionHeaderTextView = root.findViewById(R.id.module_preclusion_header);

        final TextView sem1ExamTextView = root.findViewById(R.id.module_exam_1);
        final TextView sem2ExamTextView = root.findViewById(R.id.module_exam_2);
        final TextView sem3ExamTextView = root.findViewById(R.id.module_exam_3);
        final TextView sem4ExamTextView = root.findViewById(R.id.module_exam_4);

        final TextView sem1ExamHeadingTextView = root.findViewById(R.id.module_exam_1_heading);
        final TextView sem2ExamHeadingTextView = root.findViewById(R.id.module_exam_2_heading);
        final TextView sem3ExamHeadingTextView = root.findViewById(R.id.module_exam_3_heading2);
        final TextView sem4ExamHeadingTextView = root.findViewById(R.id.module_exam_4_heading);

        final Button button = root.findViewById(R.id.button);
        final RecyclerView recyclerView = root.findViewById(R.id.disqus_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DisqusPostAdapter adapter = new DisqusPostAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getModuleObservable().observe(getViewLifecycleOwner(), m -> {
            if (m == null) {
                return;
            }

            DisqusRepository.getPosts(m.getModuleCode()).observe(getViewLifecycleOwner(),
                    postList -> adapter.setPosts(postList.getPosts()));

            codeTextView.setText(m.getModuleCode());
            titleTextView.setText(m.getTitle());
            infoTextView.setText(generateInfoText(m.getDepartment(), m.getFaculty(),
                    m.getModuleCredit()));
            semestersTextView.setText(generateSemestersText(m.getSemesters().orElse(
                    Collections.emptySet())));
            setTextView(descriptionTextView, m.getDescription());

            if (m.hasPrerequisite()) {
                prerequisiteTextView.setText(generateClickableString(m.getPrerequisite()));
                prerequisiteTextView.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                prerequisiteHeaderTextView.setVisibility(View.GONE);
                prerequisiteTextView.setVisibility(View.GONE);
            }

            if (m.hasCorequisite()) {
                corequisiteTextView
                        .setText(generateClickableString(m.getCorequisite()));
                corequisiteTextView.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                corequisiteHeaderTextView.setVisibility(View.GONE);
                corequisiteTextView.setVisibility(View.GONE);
            }

            if (m.hasPreclusion()) {
                preclusionTextView.setText(generateClickableString(m.getPreclusion()));
                preclusionTextView.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                preclusionHeaderTextView.setVisibility(View.GONE);
                preclusionTextView.setVisibility(View.GONE);
            }

            TextView[] examViews = new TextView[]{sem1ExamTextView, sem2ExamTextView,
                    sem3ExamTextView, sem4ExamTextView};
            TextView[] examHeadingViews = new TextView[]{sem1ExamHeadingTextView,
                    sem2ExamHeadingTextView, sem3ExamHeadingTextView,
                    sem4ExamHeadingTextView};

            List<ModuleSemesterDatum> semData = m.getSemesterData();

            for (int i = 0; i < semData.size(); i++) {
                ModuleSemesterDatum datum = semData.get(i);
                String examString = generateExamInfoText(datum.getExamDate(),
                        datum.getExamDuration());
                examViews[datum.getSemester().getId() - 1].setText(examString);
            }

            for (int i = 0; i < examHeadingViews.length; i++) {
                if (examViews[i].getText().length() == 0) {
                    examViews[i].setVisibility(View.GONE);
                    examHeadingViews[i].setVisibility(View.GONE);
                }
            }

            button.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(getActivity(), button);

                for (ModuleInformationSemesterDatum datum : semData) {
                    SemesterType sem = datum.getSemester();
                    popupMenu.getMenu()
                            .add(Menu.NONE, sem.getId(), Menu.NONE, sem.toString());
                }

                popupMenu.setOnMenuItemClickListener(
                        menuItem -> {
                            SemesterType semester = SemesterType.fromId(menuItem.getItemId());
                            TimetableDataSource.getInstance().insert(m.toAssignedModule(semester));
                            return true;
                        });

                popupMenu.show();
            });
        });
    }

    private String generateInfoText(String department, String faculty, Double credits) {
        return new StringJoiner(" • ").add(department).add(faculty).add(credits + " MCs")
                .toString();
    }

    private String generateSemestersText(Set<SemesterType> semesters) {
        StringJoiner joiner = new StringJoiner(" • ");

        for (SemesterType semester : semesters) {
            joiner.add(semester.toString());
        }

        return joiner.toString();
    }

    private void setTextView(TextView view, String text) {
        if (text == null || text.isEmpty()) {
            view.setVisibility(View.GONE);

        } else {
            view.setText(text);
        }
    }

    private SpannableString generateClickableString(String string) {
        if (string == null) {
            return new SpannableString("");
        }

        final Pattern pattern = Pattern.compile("(([A-Z][A-Z])(?<!AY))[A-Z]?\\d\\d\\d\\d[A-Z]?");
        Matcher matcher = pattern.matcher(string);
        SpannableString ss = new SpannableString(string);

        while (matcher.find()) {
            if (ModulesRepository.getInstance()
                    .hasModule(AcademicYear.getCurrent(), matcher.group())) {
                ss.setSpan(new ClickableModuleCode(matcher.group()), matcher.start(), matcher.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return ss;
    }

    private String generateExamInfoText(String examDate, int examDuration) {
        if (examDate == null) {
            return "No Exam";
        }

        String examDateString = ZonedDateTime.parse(examDate)
                .withZoneSameInstant(ZoneId.of("Asia/Singapore"))
                .format(DateUtil.DATE_FORMATTER_DISPLAY);
        String examDurationString = (double) examDuration / MINUTES_PER_HOUR + " hrs";
        return examDateString + " " + examDurationString;
    }

    private static class ClickableModuleCode extends ClickableSpan {
        private final String moduleCode;

        ClickableModuleCode(String moduleCode) {
            this.moduleCode = moduleCode;
        }

        @Override
        public void onClick(@NonNull View view) {
            ModuleDetailFragmentDirections.ActionNavModuleDetailSelf action =
                    ModuleDetailFragmentDirections.actionNavModuleDetailSelf(moduleCode);
            Navigation.findNavController(view).navigate(action);
        }
    }
}
