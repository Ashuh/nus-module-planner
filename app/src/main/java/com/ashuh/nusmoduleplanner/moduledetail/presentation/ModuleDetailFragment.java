package com.ashuh.nusmoduleplanner.moduledetail.presentation;

import static java.util.Objects.requireNonNull;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
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

import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.NusModulePlannerApplication;
import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.common.domain.repository.PostRepository;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiExam;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiModule;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiSemester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ModuleDetailFragment extends Fragment {
    private static final String EXAM_HEADING_FORMAT = "%s Exam";
    private static final String EXAM_INFO_FORMAT = "%s • %s";

    private TextView titleTextView;
    private TextView codeTextView;
    private TextView adminInfoTextView;
    private TextView semestersTextView;
    private TextView descriptionTextView;
    private TextView moduleRequirementsTextView;
    private TextView examInfoTextView;
    private Button button;
    private DisqusPostAdapter postAdapter;
    private ModuleDetailViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postAdapter = new DisqusPostAdapter();
        ModuleRepository moduleRepository
                = ((NusModulePlannerApplication) requireActivity().getApplication())
                .appContainer.moduleRepository;
        PostRepository postRepository
                = ((NusModulePlannerApplication) requireActivity().getApplication())
                .appContainer.postRepository;
        String moduleCode = ModuleDetailFragmentArgs.fromBundle(getArguments()).getModuleCode();
        viewModel = new ViewModelProvider(this,
                new ModuleDetailViewModelFactory(moduleRepository, postRepository,
                        AcademicYear.getCurrent(), moduleCode))
                .get(ModuleDetailViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_module_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        titleTextView = view.findViewById(R.id.module_title);
        codeTextView = view.findViewById(R.id.module_code);
        adminInfoTextView = view.findViewById(R.id.module_admin_info);
        semestersTextView = view.findViewById(R.id.module_semesters);
        descriptionTextView = view.findViewById(R.id.module_description);
        moduleRequirementsTextView = view.findViewById(R.id.module_requirements);
        examInfoTextView = view.findViewById(R.id.exam_info);
        button = view.findViewById(R.id.add_to_timetable_button);
        RecyclerView recyclerView = view.findViewById(R.id.disqus_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(postAdapter);
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getState().observe(getViewLifecycleOwner(), this::updateUi);
    }

    private void updateUi(ModuleDetailState state) {
        state.getModule().ifPresent(this::updateModuleUi);
        postAdapter.setPosts(state.getPosts());
    }

    private void updateModuleUi(UiModule module) {
        codeTextView.setText(module.getModuleCode());
        titleTextView.setText(module.getTitle());
        setAdminInfoTextView(module.getDepartment(), module.getFaculty(), module.getModuleCredit());
        setSemestersTextView(module.getSemestersOffered());
        setDescriptionTextView(module.getDescription());
        setRequirementsTextView(module.getPrerequisite(), module.getCoRequisite(),
                module.getPreclusion());
        setExamInfoTextView(module.getExams());
        setAddToTimetableButtonListener(module.getSemestersOffered());
    }

    private void setAdminInfoTextView(String department, String faculty, String moduleCredit) {
        if (department.isEmpty() || faculty.isEmpty() || moduleCredit.isEmpty()) {
            return;
        }
        String adminInfo = new StringJoiner(" • ")
                .add(department)
                .add(faculty)
                .add(moduleCredit)
                .toString();
        adminInfoTextView.setText(adminInfo);
    }

    private void setSemestersTextView(List<UiSemester> semesters) {
        String semestersText = semesters.stream()
                .map(UiSemester::getSemester)
                .collect(Collectors.joining(" • "));
        semestersTextView.setText(semestersText);
    }

    private void setDescriptionTextView(String description) {
        if (description.isEmpty()) {
            descriptionTextView.setVisibility(View.GONE);
        } else {
            descriptionTextView.setText(description);
        }
    }

    private void setRequirementsTextView(String prerequisite, String coRequisite,
                                         String preclusion) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        if (!prerequisite.isEmpty()) {
            SpannableStringBuilder prerequisiteText
                    = generateTextWithBoldHeading("Prerequisite",
                    makeModuleCodesClickable(prerequisite));
            stringBuilder.append(prerequisiteText).append("\n\n");
        }

        if (!coRequisite.isEmpty()) {
            SpannableStringBuilder corequisiteText
                    = generateTextWithBoldHeading("Corequisite",
                    makeModuleCodesClickable(coRequisite));
            stringBuilder.append(corequisiteText).append("\n\n");
        }

        if (!preclusion.isEmpty()) {
            SpannableStringBuilder preclusionText = generateTextWithBoldHeading("Preclusion",
                    makeModuleCodesClickable(preclusion));
            stringBuilder.append(preclusionText).append("\n\n");
        }

        if (stringBuilder.length() == 0) {
            moduleRequirementsTextView.setVisibility(View.GONE);
        } else {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            moduleRequirementsTextView.setText(stringBuilder);
            moduleRequirementsTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private void setExamInfoTextView(Map<String, UiExam> semesterToExam) {
        List<CharSequence> semesterExamStrings = new ArrayList<>();
        semesterToExam.forEach((semester, exam) -> {
            String examInfoHeading = String.format(EXAM_HEADING_FORMAT, semester);
            String examInfo = String.format(EXAM_INFO_FORMAT, exam.getDate(), exam.getDuration());
            SpannableStringBuilder curSemExamInfo
                    = generateTextWithBoldHeading(examInfoHeading, examInfo);
            semesterExamStrings.add(curSemExamInfo);
        });

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        for (int i = 0; i < semesterExamStrings.size(); i++) {
            stringBuilder.append(semesterExamStrings.get(i));
            if (i < semesterExamStrings.size() - 1) {
                stringBuilder.append("\n\n");
            }
        }

        examInfoTextView.setText(stringBuilder);
    }

    private void setAddToTimetableButtonListener(List<UiSemester> semesters) {
        button.setOnClickListener(view
                -> new ModuleSemesterMenu(requireContext(), button, semesters)
                .show());
    }

    private SpannableStringBuilder generateTextWithBoldHeading(CharSequence heading,
                                                               CharSequence text) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(heading).append("\n").append(text);
        StyleSpan boldStyleSpan = new StyleSpan(Typeface.BOLD);
        stringBuilder.setSpan(boldStyleSpan, 0, heading.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return stringBuilder;
    }

    private SpannableString makeModuleCodesClickable(String string) {
        if (string == null) {
            return new SpannableString("");
        }

        final String moduleCodeRegex = "[A-Z]{2,4}[1234568]\\d{3}[A-Z]{0,3}";
        final Pattern pattern = Pattern.compile(moduleCodeRegex);
        Matcher matcher = pattern.matcher(string);
        SpannableString ss = new SpannableString(string);

        while (matcher.find()) {
            ss.setSpan(new ClickableModuleCode(matcher.group()), matcher.start(), matcher.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return ss;
    }

    private static class ClickableModuleCode extends ClickableSpan {

        @NonNull
        private final String moduleCode;

        ClickableModuleCode(@NonNull String moduleCode) {
            requireNonNull(moduleCode);
            this.moduleCode = moduleCode;
        }

        @Override
        public void onClick(@NonNull View view) {
            ModuleDetailFragmentDirections.ActionNavModuleDetailSelf action =
                    ModuleDetailFragmentDirections.actionNavModuleDetailSelf(moduleCode);
            Navigation.findNavController(view).navigate(action);
        }
    }

    private static class ModuleSemesterMenu extends PopupMenu {
        private final List<UiSemester> semesters;
        Map<Integer, UiSemester> idToSemester = new HashMap<>();

        ModuleSemesterMenu(@NonNull Context context, @NonNull View anchor,
                           List<UiSemester> semesters) {
            super(context, anchor);
            requireNonNull(semesters);
            this.semesters = semesters;

            initMenuItems();
            setOnMenuItemClickListener(menuItem -> {
                UiSemester semester = idToSemester.get(menuItem.getItemId());
                assert semester != null;
                semester.onClick();
                return true;
            });
        }

        private void initMenuItems() {
            int id = 0;

            for (UiSemester semester : semesters) {
                idToSemester.put(id, semester);
                getMenu().add(Menu.NONE, id, Menu.NONE, semester.getSemester());
                id++;
            }
        }
    }
}
