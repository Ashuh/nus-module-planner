package com.ashuh.nusmoduleplanner.ui.moduledetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.data.model.disqus.PostList;
import com.ashuh.nusmoduleplanner.data.model.nusmods.AcademicYear;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;
import com.ashuh.nusmoduleplanner.data.source.nusmods.ModulesRepository;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.Module;
import com.ashuh.nusmoduleplanner.data.source.timetable.TimetableDataSource;
import com.ashuh.nusmoduleplanner.data.source.disqus.DisqusRepository;

public class ModuleDetailViewModel extends ViewModel {

    private final TimetableDataSource timetableDataSource;
    private final LiveData<Module> module;
    private final LiveData<PostList> disqusPosts;

    public ModuleDetailViewModel(TimetableDataSource timetableDataSource,
                                 AcademicYear acadYear, String moduleCode) {
        this.timetableDataSource = timetableDataSource;
        module = ModulesRepository.getInstance().getModule(acadYear, moduleCode);
        disqusPosts = DisqusRepository.getPosts(moduleCode);
    }

    public LiveData<Module> getModuleObservable() {
        return module;
    }

    public void addAssignedModule(AssignedModule module) {
        timetableDataSource.insert(module);
    }

    public LiveData<PostList> getDisqusPostsObservable() {
        return disqusPosts;
    }
}
