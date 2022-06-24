package com.ashuh.nusmoduleplanner.moduledetail.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Semester;
import com.ashuh.nusmoduleplanner.common.domain.model.post.PaginatedPosts;
import com.ashuh.nusmoduleplanner.moduledetail.domain.usecase.CreateModuleReadingUseCase;
import com.ashuh.nusmoduleplanner.moduledetail.domain.usecase.GetModuleWithPostsUseCase;
import com.ashuh.nusmoduleplanner.moduledetail.domain.model.ModuleWithPosts;
import com.ashuh.nusmoduleplanner.moduledetail.presentation.model.UiModuleDetail;

public class ModuleDetailViewModel extends ViewModel {
    private final GetModuleWithPostsUseCase getModuleWithPostsUseCase;
    private final CreateModuleReadingUseCase createModuleReadingUseCase;
    private final int primaryColor;
    private final LiveData<ModuleWithPosts> observableModuleWithPosts;
    private final LiveData<UiModuleDetail> observableState;

    public ModuleDetailViewModel(GetModuleWithPostsUseCase getModuleWithPostsUseCase,
                                 CreateModuleReadingUseCase createModuleReadingUseCase,
                                 int primaryColor, AcademicYear acadYear, String moduleCode) {
        this.getModuleWithPostsUseCase = getModuleWithPostsUseCase;
        this.createModuleReadingUseCase = createModuleReadingUseCase;
        this.primaryColor = primaryColor;
        observableModuleWithPosts = getModuleWithPostsUseCase.execute(acadYear, moduleCode);
        observableState = Transformations.map(observableModuleWithPosts, this::buildUiModuleDetail);
    }

    private UiModuleDetail buildUiModuleDetail(ModuleWithPosts moduleWithPosts) {
        UiModuleDetail.Builder builder = new UiModuleDetail.Builder();
        moduleWithPosts.getModule().ifPresent(builder::withModule);
        moduleWithPosts.getPaginatedPosts()
                .map(PaginatedPosts::getPosts)
                .ifPresent(posts -> builder.withPosts(posts, primaryColor));
        return builder.build();
    }

    public LiveData<UiModuleDetail> getState() {
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
