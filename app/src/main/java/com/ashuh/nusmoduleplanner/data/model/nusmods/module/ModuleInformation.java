package com.ashuh.nusmoduleplanner.data.model.nusmods.module;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.ModuleInformationSemesterDatum;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.workload.Workload;

import java.util.List;
import java.util.Optional;

public class ModuleInformation extends BaseDetailedModule {

    @NonNull
    private final List<ModuleInformationSemesterDatum> semesterData;

    public ModuleInformation(@NonNull String moduleCode, @NonNull String title,
                             @NonNull String acadYear, @NonNull String description,
                             @NonNull String faculty, @NonNull String department,
                             double moduleCredit, @NonNull Workload workload,
                             String preclusion, String prerequisite, String corequisite,
                             @NonNull List<ModuleInformationSemesterDatum> semesterData) {
        super(moduleCode, title, acadYear, description, faculty, department, moduleCredit, workload,
                preclusion, prerequisite, corequisite);
        this.semesterData = semesterData;
    }

    @NonNull
    public List<ModuleInformationSemesterDatum> getSemesterData() {
        return semesterData;
    }

    public Optional<ModuleInformationSemesterDatum> getSemesterDatum(SemesterType semType) {
        for (ModuleInformationSemesterDatum datum : semesterData) {
            if (datum.getSemester() == semType) {
                return Optional.of(datum);
            }
        }

        return Optional.empty();
    }
}
