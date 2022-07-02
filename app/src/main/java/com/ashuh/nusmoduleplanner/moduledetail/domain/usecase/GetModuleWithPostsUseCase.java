package com.ashuh.nusmoduleplanner.moduledetail.domain.usecase;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.post.PaginatedPosts;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.common.domain.repository.PostRepository;
import com.ashuh.nusmoduleplanner.moduledetail.domain.model.ModuleWithPosts;

public class GetModuleWithPostsUseCase {
    @NonNull
    private final ModuleRepository moduleRepository;
    @NonNull
    private final PostRepository postRepository;

    public GetModuleWithPostsUseCase(@NonNull ModuleRepository moduleRepository,
                                     @NonNull PostRepository postRepository) {
        this.moduleRepository = requireNonNull(moduleRepository);
        this.postRepository = requireNonNull(postRepository);
    }

    @NonNull
    public LiveData<ModuleWithPosts> execute(@NonNull AcademicYear academicYear,
                                             @NonNull String moduleCode) {
        LiveData<Module> observableModule
                = moduleRepository.getModule(academicYear.toString(), moduleCode);
        LiveData<PaginatedPosts> observablePosts = postRepository.getPosts(moduleCode);

        MediatorLiveData<ModuleWithPosts> observableResult = new MediatorLiveData<>();

        observableResult.addSource(observableModule, module -> {
            ModuleWithPosts result = new ModuleWithPosts(module, observablePosts.getValue());
            observableResult.setValue(result);
        });

        observableResult.addSource(observablePosts, posts -> {
            ModuleWithPosts result = new ModuleWithPosts(observableModule.getValue(), posts);
            observableResult.setValue(result);
        });

        return observableResult;
    }
}
