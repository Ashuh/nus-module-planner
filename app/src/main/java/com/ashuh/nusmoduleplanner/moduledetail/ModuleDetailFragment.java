package com.ashuh.nusmoduleplanner.moduledetail;

import static java.util.Objects.requireNonNull;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.common.MainActivity;
import com.ashuh.nusmoduleplanner.common.NusModulePlannerApplication;
import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Exam;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.util.DateUtil;
import com.ashuh.nusmoduleplanner.common.util.ColorScheme;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModuleDetailFragment extends Fragment {

    private static final String ACTION_BAR_TITLE = "Module Details";

    private TextView titleTextView;
    private TextView codeTextView;
    private TextView adminInfoTextView;
    private TextView semestersTextView;
    private TextView descriptionTextView;
    private TextView moduleRequirementsTextView;
    private TextView examInfoTextView;
    private Button button;
    private RecyclerView recyclerView;
    private ModuleDetailViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((MainActivity) requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(ACTION_BAR_TITLE);

        View root = inflater.inflate(R.layout.fragment_module_detail, container, false);
        titleTextView = root.findViewById(R.id.module_title);
        codeTextView = root.findViewById(R.id.module_code);
        adminInfoTextView = root.findViewById(R.id.module_admin_info);
        semestersTextView = root.findViewById(R.id.module_semesters);
        descriptionTextView = root.findViewById(R.id.module_description);
        moduleRequirementsTextView = root.findViewById(R.id.module_requirements);
        examInfoTextView = root.findViewById(R.id.exam_info);
        button = root.findViewById(R.id.add_to_timetable_button);
        recyclerView = root.findViewById(R.id.disqus_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String moduleCode = ModuleDetailFragmentArgs.fromBundle(getArguments()).getModuleCode();
        ModuleRepository moduleRepository
                = ((NusModulePlannerApplication) requireActivity().getApplication())
                .appContainer.moduleRepository;

        viewModel = new ViewModelProvider(this,
                new ModuleDetailViewModelFactory(moduleRepository,
                        AcademicYear.getCurrent(), moduleCode))
                .get(ModuleDetailViewModel.class);

        observeViewModel();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void observeViewModel() {
        DisqusPostAdapter adapter = new DisqusPostAdapter();
        recyclerView.setAdapter(adapter);
        viewModel.getDisqusPostsObservable().observe(getViewLifecycleOwner(),
                postList -> adapter.setPosts(postList.getPosts()));

        viewModel.getModuleObservable().observe(getViewLifecycleOwner(), module -> {
            if (module == null) {
                return;
            }

            codeTextView.setText(module.getModuleCode());
            titleTextView.setText(module.getTitle());
            setAdminInfoTextView(module);
            setSemestersTextView(module);
            setDescriptionTextView(module);
            setRequirementsTextView(module);
            setExamInfoTextView(module);
            setAddToTimetableButtonListener(module);
        });
    }

    private void setAdminInfoTextView(Module module) {
        String adminInfo = new StringJoiner(" • ")
                .add(module.getDepartment())
                .add(module.getFaculty())
                .add(module.getModuleCredit() + " MCs")
                .toString();
        adminInfoTextView.setText(adminInfo);
    }

    private void setSemestersTextView(Module module) {
        StringJoiner joiner = new StringJoiner(" • ");

        for (Semester semester : module.getSemesters()) {
            joiner.add(semester.toString());
        }

        semestersTextView.setText(joiner.toString());
    }

    private void setDescriptionTextView(Module module) {
        String description = module.getDescription();
        if (description.isEmpty()) {
            descriptionTextView.setVisibility(View.GONE);
        } else {
            descriptionTextView.setText(description);
        }
    }

    private void setRequirementsTextView(Module module) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        if (!module.getPrerequisite().isEmpty()) {
            SpannableStringBuilder prerequisiteText
                    = generateTextWithBoldHeading("Prerequisite",
                    makeModuleCodesClickable(module.getPrerequisite()));
            stringBuilder.append(prerequisiteText).append("\n\n");
        }

        if (!module.getCoRequisite().isEmpty()) {
            SpannableStringBuilder corequisiteText
                    = generateTextWithBoldHeading("Corequisite",
                    makeModuleCodesClickable(module.getCoRequisite()));
            stringBuilder.append(corequisiteText).append("\n\n");
        }

        if (!module.getPreclusion().isEmpty()) {
            SpannableStringBuilder preclusionText = generateTextWithBoldHeading("Preclusion",
                    makeModuleCodesClickable(module.getPreclusion()));
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

    private void setExamInfoTextView(Module module) {
        Map<Semester, Exam> semesterToExam = module.getExams();
        System.out.println(semesterToExam);

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        for (Semester semester : semesterToExam.keySet()) {
            String examInfoHeading = semester + " Exam";
            Exam exam = semesterToExam.get(semester);
            assert exam != null;
            String examInfo = generateExamInfoText(exam.getDate(), exam.getDuration());
            SpannableStringBuilder curSemExamInfo
                    = generateTextWithBoldHeading(examInfoHeading, examInfo);
            stringBuilder.append(curSemExamInfo).append("\n\n");
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }

        examInfoTextView.setText(stringBuilder);
    }

    private void setAddToTimetableButtonListener(Module module) {
        button.setOnClickListener(view -> new ModuleSemesterMenu(requireContext(), button, module)
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

        final Pattern pattern = Pattern.compile("(([A-Z][A-Z])(?<!AY))[A-Z]?\\d\\d\\d\\d[A-Z]?");
        Matcher matcher = pattern.matcher(string);
        SpannableString ss = new SpannableString(string);

//        while (matcher.find()) {
//            if (ModulesRepository.getInstance()
//                    .hasModule(AcademicYear.getCurrent(), matcher.group())) {
//                ss.setSpan(new ClickableModuleCode(matcher.group()), matcher.start(), matcher
//                .end(),
//                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }

        return ss;
    }

    private String generateExamInfoText(ZonedDateTime date, Duration examDuration) {
        if (date == null) {
            return "No Exam";
        }

        String examDateString = date
                .withZoneSameInstant(ZoneId.systemDefault())
                .format(DateUtil.DATE_FORMATTER_DISPLAY);
        String examDurationString = (double) examDuration.toHours() + " hrs";
        return examDateString + " " + examDurationString;
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

    private class ModuleSemesterMenu extends PopupMenu {
        private static final int ID_SEMESTER_1 = 1;
        private static final int ID_SEMESTER_2 = 2;
        private static final int ID_SPECIAL_TERM_1 = 3;
        private static final int ID_SPECIAL_TERM_2 = 4;

        @NonNull
        private final Module module;

        ModuleSemesterMenu(@NonNull Context context, @NonNull View anchor, @NonNull Module module) {
            super(context, anchor);
            requireNonNull(module);
            this.module = module;

            initMenuItems();
            setOnMenuItemClickListener(menuItem -> {
                Semester semester;
                switch (menuItem.getItemId()) {
                    case ID_SEMESTER_1:
                        semester = Semester.SEMESTER_1;
                        break;
                    case ID_SEMESTER_2:
                        semester = Semester.SEMESTER_2;
                        break;
                    case ID_SPECIAL_TERM_1:
                        semester = Semester.SPECIAL_TERM_1;
                        break;
                    case ID_SPECIAL_TERM_2:
                        semester = Semester.SPECIAL_TERM_2;
                        break;
                    default:
                        throw new IllegalStateException(
                                "Unknown menu item id: " + menuItem.getItemId());
                }


                Color color = ColorScheme.GOOGLE.getRandomColor();
                ModuleReading moduleReading = ModuleReading.withDefaultLessonMapping(module,
                        semester, color);
                viewModel.addTimetableEntry(moduleReading);
                return true;
            });

        }

        private void initMenuItems() {
            for (Semester semester : module.getSemesters()) {
                int id;
                switch (semester) {
                    case SEMESTER_1:
                        id = ID_SEMESTER_1;
                        break;
                    case SEMESTER_2:
                        id = ID_SEMESTER_2;
                        break;
                    case SPECIAL_TERM_1:
                        id = ID_SPECIAL_TERM_1;
                        break;
                    case SPECIAL_TERM_2:
                        id = ID_SPECIAL_TERM_2;
                        break;
                    default:
                        throw new IllegalStateException("Unknown semester type: " + semester);
                }
                getMenu().add(Menu.NONE, id, Menu.NONE, semester.toString());
            }
        }
    }
}
