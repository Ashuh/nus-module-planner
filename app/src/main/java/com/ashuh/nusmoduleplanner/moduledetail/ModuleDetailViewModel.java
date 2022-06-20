package com.ashuh.nusmoduleplanner.moduledetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.common.data.remote.source.PostRemoteDataSource;
import com.ashuh.nusmoduleplanner.common.data.repository.AppPostRepository;
import com.ashuh.nusmoduleplanner.common.data.remote.api.DisqusApi;
import com.ashuh.nusmoduleplanner.common.data.remote.api.DisqusApiBuilder;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleReading;
import com.ashuh.nusmoduleplanner.common.domain.model.post.PaginatedPosts;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;

public class ModuleDetailViewModel extends ViewModel {

    private final ModuleRepository moduleRepository;
    private final LiveData<Module> module;
    private final LiveData<PaginatedPosts> disqusPosts;

    public ModuleDetailViewModel(ModuleRepository moduleRepository, AcademicYear acadYear,
                                 String moduleCode) {
        this.moduleRepository = moduleRepository;
        module = moduleRepository.getModule(acadYear.toString(), moduleCode);
        DisqusApi disqusApi = DisqusApiBuilder.getInstance().getApi();
        PostRemoteDataSource postRemoteDataSource = new PostRemoteDataSource(disqusApi);
        AppPostRepository postRepository = new AppPostRepository(postRemoteDataSource);
        disqusPosts = postRepository.getPosts(moduleCode);
    }

    public LiveData<Module> getModuleObservable() {
        return module;
    }

    public void addTimetableEntry(ModuleReading entry) {
        moduleRepository.storeModuleReading(entry);
    }

    public LiveData<PaginatedPosts> getDisqusPostsObservable() {
        return disqusPosts;
    }
}
