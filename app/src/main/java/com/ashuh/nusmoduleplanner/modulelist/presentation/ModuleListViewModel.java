package com.ashuh.nusmoduleplanner.modulelist.presentation;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ashuh.nusmoduleplanner.common.domain.model.module.AcademicYear;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleInfo;
import com.ashuh.nusmoduleplanner.modulelist.domain.usecase.GetModuleInfoUseCase;
import com.ashuh.nusmoduleplanner.modulelist.presentation.model.UiModuleInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ModuleListViewModel extends ViewModel {
    private static final Predicate<ModuleInfo> PREDICATE_SHOW_ALL = module -> true;

    @NonNull
    private final GetModuleInfoUseCase getModuleInfoUseCase;
    private final LiveData<List<ModuleInfo>> allModules;
    private final MediatorLiveData<ModuleListState> observableState;
    private final MutableLiveData<Predicate<ModuleInfo>> filterPredicate
            = new MutableLiveData<>(PREDICATE_SHOW_ALL);

    public ModuleListViewModel(@NonNull GetModuleInfoUseCase getModuleInfoUseCase) {
        requireNonNull(getModuleInfoUseCase);
        this.getModuleInfoUseCase = getModuleInfoUseCase;
        allModules = getModuleInfoUseCase.execute(AcademicYear.getCurrent());

        observableState = new MediatorLiveData<>();
        observableState.addSource(allModules, unfiltered -> {
            Predicate<ModuleInfo> predicate = filterPredicate.getValue();
            ModuleListState state = buildState(unfiltered, predicate);
            observableState.setValue(state);
        });
        observableState.addSource(filterPredicate, predicate -> {
            List<ModuleInfo> unfiltered
                    = requireNonNullElse(allModules.getValue(), Collections.emptyList());
            ModuleListState state = buildState(unfiltered, predicate);
            observableState.setValue(state);
        });
    }

    public ModuleListState buildState(Collection<ModuleInfo> modules,
                                      Predicate<ModuleInfo> predicate) {
        List<UiModuleInfo> uiModules = modules.stream()
                .filter(predicate)
                .map(ModuleListViewModel::mapModuleInfo)
                .collect(Collectors.toList());
        return new ModuleListState(uiModules);
    }

    public static UiModuleInfo mapModuleInfo(ModuleInfo moduleInfo) {
        String moduleCode = moduleInfo.getModuleCode();
        String title = moduleInfo.getTitle();
        String department = moduleInfo.getDepartment();
        String moduleCredit = moduleInfo.getModuleCredit().toString();
        return new UiModuleInfo(moduleCode, title, department, moduleCredit);
    }

    public LiveData<ModuleListState> getState() {
        return observableState;
    }

    public void filter(String query) {
        Predicate<ModuleInfo> predicate
                = (module) -> module.getModuleCode().contains(query.toUpperCase());
        filterPredicate.setValue(predicate);
    }
}
