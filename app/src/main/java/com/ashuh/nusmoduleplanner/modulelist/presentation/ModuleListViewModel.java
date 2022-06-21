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

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ModuleListViewModel extends ViewModel {
    private static final Predicate<ModuleInfo> PREDICATE_SHOW_ALL = module -> true;

    @NonNull
    private final GetModuleInfoUseCase getModuleInfoUseCase;
    private final LiveData<List<ModuleInfo>> allModules;
    private final MediatorLiveData<List<ModuleInfo>> filteredModules;
    private final MutableLiveData<Predicate<ModuleInfo>> filterPredicate
            = new MutableLiveData<>(PREDICATE_SHOW_ALL);

    public ModuleListViewModel(@NonNull GetModuleInfoUseCase getModuleInfoUseCase) {
        requireNonNull(getModuleInfoUseCase);
        this.getModuleInfoUseCase = getModuleInfoUseCase;
        allModules = getModuleInfoUseCase.execute(AcademicYear.getCurrent());

        filteredModules = new MediatorLiveData<>();
        filteredModules.addSource(allModules, unfiltered -> {
            Predicate<ModuleInfo> predicate = filterPredicate.getValue();
            assert predicate != null;
            List<ModuleInfo> filtered = unfiltered.stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
            filteredModules.setValue(filtered);
        });
        filteredModules.addSource(filterPredicate, predicate -> {
            List<ModuleInfo> unfiltered
                    = requireNonNullElse(allModules.getValue(), Collections.emptyList());
            List<ModuleInfo> filtered = unfiltered.stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
            filteredModules.setValue(filtered);
        });
    }

    public LiveData<List<ModuleInfo>> getModuleListObservable() {
        return filteredModules;
    }

    public void filter(String query) {
        Predicate<ModuleInfo> predicate
                = (module) -> module.getModuleCode().contains(query.toUpperCase());
        filterPredicate.setValue(predicate);
    }
}
