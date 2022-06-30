package com.ashuh.nusmoduleplanner.moduledetail.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.post.PaginatedPosts;
import com.ashuh.nusmoduleplanner.common.domain.model.post.Post;
import com.ashuh.nusmoduleplanner.moduledetail.domain.model.ModuleWithPosts;
import com.ashuh.nusmoduleplanner.moduledetail.domain.usecase.CreateModuleReadingUseCase;
import com.ashuh.nusmoduleplanner.moduledetail.domain.usecase.GetModuleWithPostsUseCase;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiExam;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiModule;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiPost;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModuleDetailViewModel extends ViewModel {
    private static final List<ChronoUnit> AGE_UNITS = List.of(ChronoUnit.YEARS, ChronoUnit.MONTHS,
            ChronoUnit.DAYS, ChronoUnit.HOURS, ChronoUnit.MINUTES);
    private static final String AGE_TEXT = "%s %s ago";

    private final GetModuleWithPostsUseCase getModuleWithPostsUseCase;
    private final CreateModuleReadingUseCase createModuleReadingUseCase;
    private final LiveData<ModuleWithPosts> observableModuleWithPosts;
    private final LiveData<ModuleDetailState> observableState;

    public ModuleDetailViewModel(GetModuleWithPostsUseCase getModuleWithPostsUseCase,
                                 CreateModuleReadingUseCase createModuleReadingUseCase,
                                 AcademicYear acadYear, String moduleCode) {
        this.getModuleWithPostsUseCase = getModuleWithPostsUseCase;
        this.createModuleReadingUseCase = createModuleReadingUseCase;
        observableModuleWithPosts = getModuleWithPostsUseCase.execute(acadYear, moduleCode);
        observableState = Transformations
                .map(observableModuleWithPosts, ModuleDetailViewModel::buildState);
    }

    private static ModuleDetailState buildState(ModuleWithPosts moduleWithPosts) {
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

    private static UiModule mapModule(Module module) {
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
                        e -> UiExam.fromDomain(e.getValue()),
                        (x, y) -> y,
                        LinkedHashMap::new)
                );
        List<String> semestersOffered = module.getSemesters().stream()
                .sorted()
                .map(Semester::toString)
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

    public LiveData<ModuleDetailState> getState() {
        return observableState;
    }

    public void createModuleReading(Semester semester) {
        ModuleWithPosts moduleWithPosts = observableModuleWithPosts.getValue();
        if (moduleWithPosts == null) {
            return;
        }
        moduleWithPosts.getModule()
                .ifPresent(module -> createModuleReadingUseCase.execute(module, semester));
    }
}
