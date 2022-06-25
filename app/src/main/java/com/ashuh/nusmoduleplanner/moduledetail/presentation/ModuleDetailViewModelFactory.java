package com.ashuh.nusmoduleplanner.moduledetail.presentation;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.common.domain.repository.PostRepository;
import com.ashuh.nusmoduleplanner.moduledetail.domain.usecase.CreateModuleReadingUseCase;
import com.ashuh.nusmoduleplanner.moduledetail.domain.usecase.GetModuleWithPostsUseCase;

import org.jetbrains.annotations.NotNull;

public class ModuleDetailViewModelFactory implements ViewModelProvider.Factory {
    private final ModuleRepository moduleRepository;
    private final PostRepository postRepository;
    private final AcademicYear acadYear;
    private final String moduleCode;

    public ModuleDetailViewModelFactory(ModuleRepository moduleRepository,
                                        PostRepository postRepository, AcademicYear acadYear,
                                        String moduleCode) {
        this.moduleRepository = moduleRepository;
        this.postRepository = postRepository;
        this.acadYear = acadYear;
        this.moduleCode = moduleCode;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> aClass) {
        GetModuleWithPostsUseCase getModuleWithPostsUseCase
                = new GetModuleWithPostsUseCase(moduleRepository, postRepository);
        CreateModuleReadingUseCase createModuleReadingUseCase
                = new CreateModuleReadingUseCase(moduleRepository);
        return (T) new ModuleDetailViewModel(getModuleWithPostsUseCase, createModuleReadingUseCase,
                acadYear, moduleCode);
    }
}
