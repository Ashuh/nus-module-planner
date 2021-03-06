package com.ashuh.nusmoduleplanner.moduledetail.presentation;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Exam;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.post.PaginatedPosts;
import com.ashuh.nusmoduleplanner.common.domain.model.post.Post;
import com.ashuh.nusmoduleplanner.common.util.DateUtil;
import com.ashuh.nusmoduleplanner.common.util.StringUtil;
import com.ashuh.nusmoduleplanner.moduledetail.domain.model.ModuleWithPosts;
import com.ashuh.nusmoduleplanner.moduledetail.domain.usecase.CreateModuleReadingUseCase;
import com.ashuh.nusmoduleplanner.moduledetail.domain.usecase.GetModuleWithPostsUseCase;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiExam;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiModule;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiPost;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiSemester;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ModuleDetailViewModel extends ViewModel {
    private static final List<ChronoUnit> AGE_UNITS = List.of(ChronoUnit.YEARS, ChronoUnit.MONTHS,
            ChronoUnit.DAYS, ChronoUnit.HOURS, ChronoUnit.MINUTES);
    private static final int MINUTES_PER_HOUR = 60;
    private static final String AGE_TEXT = "%s %s ago";
    private static final String DURATION_FORMAT = "%s hrs";

    @NonNull
    private final GetModuleWithPostsUseCase getModuleWithPostsUseCase;
    @NonNull
    private final CreateModuleReadingUseCase createModuleReadingUseCase;
    @NonNull
    private final LiveData<ModuleWithPosts> observableModuleWithPosts;
    @NonNull
    private final MediatorLiveData<ModuleDetailState> observableState;

    public ModuleDetailViewModel(@NonNull GetModuleWithPostsUseCase getModuleWithPostsUseCase,
                                 @NonNull CreateModuleReadingUseCase createModuleReadingUseCase,
                                 @NonNull AcademicYear acadYear, @NonNull String moduleCode) {
        this.getModuleWithPostsUseCase = requireNonNull(getModuleWithPostsUseCase);
        this.createModuleReadingUseCase = requireNonNull(createModuleReadingUseCase);
        observableModuleWithPosts = getModuleWithPostsUseCase.execute(acadYear, moduleCode);
        observableState = new MediatorLiveData<>();
        observableState.setValue(ModuleDetailState.loading());
        observableState.addSource(observableModuleWithPosts, moduleWithPosts -> {
            observableState.setValue(buildState(moduleWithPosts));
        });
    }

    private ModuleDetailState buildState(ModuleWithPosts moduleWithPosts) {
        ModuleDetailState.Builder builder = new ModuleDetailState.Builder();
        moduleWithPosts.getModule()
                .ifPresent(module -> {
                    UiModule uiModule = mapModule(module);
                    builder.setModule(uiModule);
                });
        moduleWithPosts.getPaginatedPosts()
                .map(PaginatedPosts::getPosts)
                .ifPresent(posts -> {
                    List<UiPost> uiPosts = posts.stream()
                            .map(ModuleDetailViewModel::mapPost)
                            .collect(Collectors.toList());
                    builder.setPosts(uiPosts);
                });
        return builder.build();
    }

    private UiModule mapModule(Module module) {
        String moduleCode = module.getModuleCode();
        String title = module.getTitle();
        String moduleCredit = module.getModuleCredit().toString();
        String department = module.getDepartment();
        String faculty = module.getFaculty();
        String description = module.getDescription();
        String prerequisite = module.getPrerequisite();
        String coRequisite = module.getCoRequisite();
        String preclusion = module.getPreclusion();
        Map<String, UiExam> semesterToExam = module.getExams().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        e -> e.getKey().toString(),
                        e -> mapExam(e.getValue()),
                        (x, y) -> y,
                        LinkedHashMap::new)
                );
        List<UiSemester> semestersOffered = module.getSemesters().stream()
                .sorted()
                .map(this::mapSemester)
                .collect(Collectors.toList());

        return new UiModule(moduleCode, title, moduleCredit, department, faculty, description,
                prerequisite, coRequisite, preclusion, semesterToExam, semestersOffered);
    }

    private static UiPost mapPost(Post post) {
        String name = post.getAuthor().getName();
        String age = calculatePostAge(post.getCreatedAt());
        String message = post.getMessage();
        return new UiPost(name, age, message);
    }

    private static UiExam mapExam(Exam exam) {
        String date = DateUtil.formatZonedDateTimeForDisplay(exam.getDate());
        double hours = (double) exam.getDuration().toMinutes() / MINUTES_PER_HOUR;
        String hoursFormatted = StringUtil.doubleWithoutTrailingZeros(hours);
        String duration = String.format(Locale.ENGLISH, DURATION_FORMAT, hoursFormatted);
        return new UiExam(date, duration);
    }

    private UiSemester mapSemester(Semester semester) {
        Runnable onClick = () -> createModuleReading(semester);
        return new UiSemester(semester.toString(), onClick);
    }

    private static String calculatePostAge(ZonedDateTime postTime) {
        ZonedDateTime now = ZonedDateTime.now();

        long age = 0;
        ChronoUnit ageUnit = AGE_UNITS.get(AGE_UNITS.size() - 1);

        for (ChronoUnit unit : AGE_UNITS) {
            age = unit.between(postTime, now);
            if (age > 0) {
                ageUnit = unit;
                break;
            }
        }

        return String.format(AGE_TEXT, age, ageUnit);
    }

    public void createModuleReading(Semester semester) {
        ModuleWithPosts moduleWithPosts = observableModuleWithPosts.getValue();
        if (moduleWithPosts == null) {
            return;
        }
        moduleWithPosts.getModule()
                .ifPresent(module -> createModuleReadingUseCase.execute(module, semester));
    }

    public LiveData<ModuleDetailState> getState() {
        return observableState;
    }
}
