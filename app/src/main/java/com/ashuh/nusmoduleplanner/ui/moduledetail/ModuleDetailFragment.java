package com.ashuh.nusmoduleplanner.ui.moduledetail;

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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashuh.nusmoduleplanner.MainActivity;
import com.ashuh.nusmoduleplanner.R;
import com.ashuh.nusmoduleplanner.data.model.nusmods.AcademicYear;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.Module;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.ModuleInformationSemesterDatum;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.ModuleSemesterDatum;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.util.DateUtil;
import com.ashuh.nusmoduleplanner.data.source.nusmods.ModulesRepository;
import com.ashuh.nusmoduleplanner.data.source.timetable.TimetableDataSource;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModuleDetailFragment extends Fragment {

    private static final String ACTION_BAR_TITLE = "Module Details";
    private static final int MINUTES_PER_HOUR = 60;

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
        viewModel = new ViewModelProvider(this,
                new ModuleDetailViewModelFactory(AcademicYear.getCurrent(), moduleCode))
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

        for (SemesterType semester : module.getSemesters().orElse(Collections.emptyList())) {
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

        if (module.hasPrerequisite()) {
            SpannableStringBuilder prerequisiteText
                    = generateTextWithBoldHeading("Prerequisite",
                    makeModuleCodesClickable(module.getPrerequisite()));
            stringBuilder.append(prerequisiteText).append("\n\n");
        }

        if (module.hasCorequisite()) {
            SpannableStringBuilder corequisiteText
                    = generateTextWithBoldHeading("Corequisite",
                    makeModuleCodesClickable(module.getCorequisite()));
            stringBuilder.append(corequisiteText).append("\n\n");
        }

        if (module.hasPreclusion()) {
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
        List<ModuleSemesterDatum> semData = module.getSemesterData();
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        for (int i = 0; i < semData.size(); i++) {
            ModuleSemesterDatum datum = semData.get(i);

            String examInfoHeading = datum.getSemester() + " Exam";
            String examInfo = generateExamInfoText(datum.getExamDate(), datum.getExamDuration());

            SpannableStringBuilder curSemExamInfo
                    = generateTextWithBoldHeading(examInfoHeading, examInfo);
            stringBuilder.append(curSemExamInfo).append("\n\n");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
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

        while (matcher.find()) {
            if (ModulesRepository.getInstance()
                    .hasModule(AcademicYear.getCurrent(), matcher.group())) {
                ss.setSpan(new ClickableModuleCode(matcher.group()), matcher.start(), matcher.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return ss;
    }

    private String generateExamInfoText(ZonedDateTime examDate, int examDuration) {
        if (examDate == null) {
            return "No Exam";
        }

        String examDateString = examDate
                .withZoneSameInstant(ZoneId.systemDefault())
                .format(DateUtil.DATE_FORMATTER_DISPLAY);
        String examDurationString = (double) examDuration / MINUTES_PER_HOUR + " hrs";
        return examDateString + " " + examDurationString;
    }

    private static class ModuleSemesterMenu extends PopupMenu {

        @NonNull
        private final Module module;

        ModuleSemesterMenu(@NonNull Context context, @NonNull View anchor, @NonNull Module module) {
            super(context, anchor);
            requireNonNull(module);
            this.module = module;

            initMenuItems();
            setOnMenuItemClickListener(menuItem -> {
                SemesterType semester = SemesterType.fromId(menuItem.getItemId());
                addModuleToSemesterTimetable(semester);
                return true;
            });

        }

        private void initMenuItems() {
            for (ModuleInformationSemesterDatum datum : module.getSemesterData()) {
                SemesterType sem = datum.getSemester();
                getMenu().add(Menu.NONE, sem.getId(), Menu.NONE, sem.toString());
            }
        }

        private void addModuleToSemesterTimetable(SemesterType semester) {
            TimetableDataSource.getInstance().insert(module.toAssignedModule(semester));
        }
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
}
