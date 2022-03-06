package com.ashuh.nusmoduleplanner.ui.moduledetail;

import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.MainActivity;
import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.data.model.nusmods.ModuleDetail;
import com.ashuh.nusmoduleplanner.data.model.nusmods.Semester;
import com.ashuh.nusmoduleplanner.data.model.nusmods.SemesterDetail;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.source.DisqusRepository;
import com.ashuh.nusmoduleplanner.data.source.ModulesRepository;
import com.ashuh.nusmoduleplanner.data.source.TimetableDataSource;
import com.ashuh.nusmoduleplanner.util.AcademicYear;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ModuleDetailFragment extends Fragment {
    private TextView titleTextView;
    private TextView codeTextView;
    private TextView infoTextView;
    private TextView semestersTextView;
    private TextView descriptionTextView;
    private TextView prerequisiteTextView;
    private TextView corequisiteTextView;
    private TextView preclusionTextView;
    private TextView prerequisiteHeaderTextView;
    private TextView corequisiteHeaderTextView;
    private TextView preclusionHeaderTextView;
    private TextView sem3ExamTextView;
    private TextView sem1ExamTextView;
    private TextView sem2ExamTextView;
    private TextView sem4ExamTextView;
    private TextView sem1ExamHeadingTextView;
    private TextView sem2ExamHeadingTextView;
    private TextView sem3ExamHeadingTextView;
    private TextView sem4ExamHeadingTextView;
    private Button button;
    private RecyclerView recyclerView;

    private ModuleDetailViewModel viewModel;
    private DisqusPostAdapter disqusPostAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar())
                .setTitle("Module Details");

        View root = inflater.inflate(R.layout.fragment_module_detail, container, false);
        titleTextView = root.findViewById(R.id.module_title);
        codeTextView = root.findViewById(R.id.module_code);
        infoTextView = root.findViewById(R.id.module_info);
        semestersTextView = root.findViewById(R.id.module_semesters);
        descriptionTextView = root.findViewById(R.id.module_description);
        prerequisiteTextView = root.findViewById(R.id.module_prerequisite);
        corequisiteTextView = root.findViewById(R.id.module_corequisite);
        preclusionTextView = root.findViewById(R.id.module_preclusion);
        prerequisiteHeaderTextView = root.findViewById(R.id.module_prerequisite_header);
        corequisiteHeaderTextView = root.findViewById(R.id.module_corequisite_header);
        preclusionHeaderTextView = root.findViewById(R.id.module_preclusion_header);
        sem1ExamTextView = root.findViewById(R.id.module_exam_1);
        sem2ExamTextView = root.findViewById(R.id.module_exam_2);
        sem3ExamTextView = root.findViewById(R.id.module_exam_3);
        sem4ExamTextView = root.findViewById(R.id.module_exam_4);
        sem1ExamHeadingTextView = root.findViewById(R.id.module_exam_1_heading);
        sem2ExamHeadingTextView = root.findViewById(R.id.module_exam_2_heading);
        sem3ExamHeadingTextView = root.findViewById(R.id.module_exam_3_heading2);
        sem4ExamHeadingTextView = root.findViewById(R.id.module_exam_4_heading);
        button = root.findViewById(R.id.button);
        recyclerView = root.findViewById(R.id.disqus_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String moduleCode = ModuleDetailFragmentArgs.fromBundle(getArguments()).getModuleCode();

        viewModel = new ViewModelProvider(this,
                new ModuleDetailViewModelFactory(AcademicYear.getCurrent(), moduleCode))
                .get(ModuleDetailViewModel.class);

        disqusPostAdapter = new DisqusPostAdapter();
        DisqusRepository.getInstance().getPosts(moduleCode).observe(getViewLifecycleOwner(),
                postList -> disqusPostAdapter.setPosts(postList.getPosts()));
        recyclerView.setAdapter(disqusPostAdapter);

        observeViewModel();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private String generateInfoText(String department, String faculty, Double credits) {
        return new StringJoiner(" • ").add(department).add(faculty).add(credits + " MCs")
                .toString();
    }

    private String generateSemestersText(List<SemesterDetail> semesterDetailList) {
        StringJoiner joiner = new StringJoiner(" • ");

        for (SemesterDetail data : semesterDetailList) {
            joiner.add(data.getSemester().toString());
        }

        return joiner.toString();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void observeViewModel() {
        viewModel.getModuleObservable().observe(getViewLifecycleOwner(), module -> {
            if (module == null) {
                return;
            }

            setTextViews(module);
            setButton(module);
        });
    }

    private void setButton(ModuleDetail module) {
        button.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(requireActivity(), button);

            for (SemesterDetail data : module.getDetailedSemesters()) {
                Semester sem = data.getSemester();
                popupMenu.getMenu()
                        .add(Menu.NONE, sem.getId(), Menu.NONE, sem.toString());
            }

            popupMenu.setOnMenuItemClickListener(
                    menuItem -> {
                        TimetableDataSource.getInstance().insert(new AssignedModule(
                                Semester.fromId(menuItem.getItemId()), module));
                        return true;
                    });

            popupMenu.show();
        });
    }

    private void setTextViews(ModuleDetail module) {
        codeTextView.setText(module.getModuleCode());
        titleTextView.setText(module.getTitle());
        infoTextView.setText(generateInfoText(module.getDepartment(), module.getFaculty(),
                module.getCredit()));
        semestersTextView.setText(generateSemestersText(module.getDetailedSemesters()));

        setOptionalTextView(descriptionTextView, module.getDescription());
        setOptionalTextView(prerequisiteTextView, generateClickableString(module.getPrerequisite()),
                prerequisiteHeaderTextView);
        prerequisiteTextView.setMovementMethod(LinkMovementMethod.getInstance());
        setOptionalTextView(corequisiteTextView, generateClickableString(module.getCorequisite()),
                corequisiteHeaderTextView);
        corequisiteTextView.setMovementMethod(LinkMovementMethod.getInstance());
        setOptionalTextView(preclusionTextView, generateClickableString(module.getPreclusion()),
                preclusionHeaderTextView);
        preclusionTextView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView[] examViews = new TextView[]{sem1ExamTextView, sem2ExamTextView,
                sem3ExamTextView, sem4ExamTextView};
        TextView[] examHeadingViews = new TextView[]{sem1ExamHeadingTextView,
                sem2ExamHeadingTextView, sem3ExamHeadingTextView,
                sem4ExamHeadingTextView};

        List<SemesterDetail> semData = module.getDetailedSemesters();
        List<String> examStrings = Arrays.asList("", "", "", "");

        for (int i = 0; i < semData.size(); i++) {
            SemesterDetail d = semData.get(i);

            String examDate = d.getExamDate().orElse("No Exam");
            String examDuration = d.hasExam() ? d.getExamDuration().toString().substring(2)
                    .replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase()
                    : "";

            examStrings.set(d.getSemester().getId() - 1, examDate + " " + examDuration);
        }

        for (int i = 0; i < examHeadingViews.length; i++) {
            setOptionalTextView(examViews[i], examStrings.get(i), examHeadingViews[i]);
        }
    }

    private void setOptionalTextView(TextView view, CharSequence text, TextView... dependentViews) {
        if (text == null || text.length() == 0) {
            view.setVisibility(View.GONE);

            for (TextView dependentView : dependentViews) {
                dependentView.setVisibility(View.GONE);
            }
        } else {
            view.setText(text);
        }
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
