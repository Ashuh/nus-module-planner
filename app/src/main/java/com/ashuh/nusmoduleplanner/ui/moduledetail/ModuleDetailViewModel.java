package com.ashuh.nusmoduleplanner.ui.moduledetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.data.model.disqus.PostList;
import com.ashuh.nusmoduleplanner.data.model.nusmods.AcademicYear;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.Module;
import com.ashuh.nusmoduleplanner.data.source.disqus.DisqusRepository;
import com.ashuh.nusmoduleplanner.data.source.nusmods.ModulesRepository;

public class ModuleDetailViewModel extends ViewModel {

    private final LiveData<Module> module;
    private final LiveData<PostList> disqusPosts;

    public ModuleDetailViewModel(AcademicYear acadYear, String moduleCode) {
        module = ModulesRepository.getInstance().getModule(acadYear, moduleCode);
        disqusPosts = DisqusRepository.getPosts(moduleCode);
    }

    public LiveData<Module> getModuleObservable() {
        return module;
    }

    public LiveData<PostList> getDisqusPostsObservable() {
        return disqusPosts;
    }
}
