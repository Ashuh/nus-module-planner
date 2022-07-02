package com.ashuh.nusmoduleplanner.moduledetail.presentation;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.common.domain.repository.PostRepository;
import com.ashuh.nusmoduleplanner.moduledetail.domain.usecase.CreateModuleReadingUseCase;
import com.ashuh.nusmoduleplanner.moduledetail.domain.usecase.GetModuleWithPostsUseCase;

public class ModuleDetailViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final ModuleRepository moduleRepository;
    @NonNull
    private final PostRepository postRepository;
    @NonNull
    private final AcademicYear acadYear;
    @NonNull
    private final String moduleCode;

    public ModuleDetailViewModelFactory(@NonNull ModuleRepository moduleRepository,
                                        @NonNull PostRepository postRepository,
                                        @NonNull AcademicYear acadYear,
                                        @NonNull String moduleCode) {
        this.moduleRepository = requireNonNull(moduleRepository);
        this.postRepository = requireNonNull(postRepository);
        this.acadYear = requireNonNull(acadYear);
        this.moduleCode = requireNonNull(moduleCode);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        GetModuleWithPostsUseCase getModuleWithPostsUseCase
                = new GetModuleWithPostsUseCase(moduleRepository, postRepository);
        CreateModuleReadingUseCase createModuleReadingUseCase
                = new CreateModuleReadingUseCase(moduleRepository);
        return (T) new ModuleDetailViewModel(getModuleWithPostsUseCase, createModuleReadingUseCase,
                acadYear, moduleCode);
    }
}
