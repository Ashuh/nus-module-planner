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
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.common.domain.repository.PostRepository;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiExam;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiModuleDetail;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
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
        int primaryColor = requireContext()
                .getResources()
                .getColor(R.color.primary, requireContext().getTheme());
        viewModel = new ViewModelProvider(this,
                new ModuleDetailViewModelFactory(moduleRepository, postRepository, primaryColor,
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
        super.onViewCreated(view, savedInstanceState);
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

    private void updateUi(UiModuleDetail state) {
        codeTextView.setText(state.getModuleCode());
        titleTextView.setText(state.getTitle());
        setAdminInfoTextView(state.getDepartment(), state.getFaculty(), state.getModuleCredit());
        setSemestersTextView(state.getSemestersOffered());
        setDescriptionTextView(state.getDescription());
        setRequirementsTextView(state.getPrerequisite(), state.getCoRequisite(),
                state.getPreclusion());
        setExamInfoTextView(state.getExams());
        setAddToTimetableButtonListener(state.getSemestersOffered());
        postAdapter.setPosts(state.getPosts());
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

    private void setSemestersTextView(Collection<Semester> semesters) {
        String semestersText = semesters.stream()
                .sorted()
                .map(Semester::toString)
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

    private void setExamInfoTextView(Map<Semester, UiExam> semesterToExam) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        semesterToExam.keySet().stream().sorted()
                .forEach(semester -> {
                    UiExam exam = semesterToExam.get(semester);
                    if (exam == null) {
                        return;
                    }
                    String examInfoHeading = String.format(EXAM_HEADING_FORMAT, semester);
                    String examInfo = String.format(EXAM_INFO_FORMAT, exam.getDate(),
                            exam.getDuration());
                    SpannableStringBuilder curSemExamInfo
                            = generateTextWithBoldHeading(examInfoHeading, examInfo);
                    stringBuilder.append(curSemExamInfo).append("\n\n");
                });

        if (stringBuilder.length() > 0) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }

        examInfoTextView.setText(stringBuilder);
    }

    private void setAddToTimetableButtonListener(Set<Semester> semesters) {
        button.setOnClickListener(
                view -> new ModuleSemesterMenu(requireContext(), button, semesters)
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

    private class ModuleSemesterMenu extends PopupMenu {
        private static final int ID_SEMESTER_1 = 1;
        private static final int ID_SEMESTER_2 = 2;
        private static final int ID_SPECIAL_TERM_1 = 3;
        private static final int ID_SPECIAL_TERM_2 = 4;

        private final Set<Semester> semesters;

        ModuleSemesterMenu(@NonNull Context context, @NonNull View anchor,
                           Set<Semester> semesters) {
            super(context, anchor);
            requireNonNull(semesters);
            this.semesters = semesters;

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

                viewModel.createModuleReading(semester);
                return true;
            });
        }

        private void initMenuItems() {
            for (Semester semester : semesters) {
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
