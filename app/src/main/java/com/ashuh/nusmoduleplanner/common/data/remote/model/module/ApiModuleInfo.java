package com.ashuh.nusmoduleplanner.common.data.remote.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.data.remote.model.module.semesterdatum.ApiModuleInfoSemesterDatum;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleCredit;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleInfo;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleInfoSemesterDatum;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Workload;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiModuleInfo extends ApiBaseDetailedModule {
    @NonNull
    @SerializedName("semesterData")
    private final List<ApiModuleInfoSemesterDatum> semesterData;

    public ApiModuleInfo(String moduleCode, String title, String description, String faculty,
                         String department, ApiWorkloadResponse workload, String preclusion,
                         String prerequisite, String coRequisite, double moduleCredit,
                         @NonNull List<ApiModuleInfoSemesterDatum> semesterData) {
        super(moduleCode, title, description, faculty, department, workload, preclusion,
                prerequisite, coRequisite, moduleCredit);
        this.semesterData = requireNonNull(semesterData);
    }

    @NonNull
    public List<ApiModuleInfoSemesterDatum> getSemesterData() {
        return semesterData;
    }

    @NonNull
    public ModuleInfo toDomain() {
        Workload domainWorkload = getWorkload()
                .map(ApiWorkloadResponse::toDomain)
                .orElse(null);
        String domainPreclusion = getPreclusion();
        String domainPrerequisite = getPrerequisite();
        String domainCoRequisite = getCoRequisite();
        ModuleCredit domainModuleCredit = new ModuleCredit(getModuleCredit());

        List<ModuleInfoSemesterDatum> domainSemesterData = semesterData.stream()
                .map(ApiModuleInfoSemesterDatum::toDomain)
                .collect(java.util.stream.Collectors.toList());

        return new ModuleInfo(moduleCode, title, description, domainModuleCredit, department,
                faculty, domainWorkload, domainPrerequisite, domainPreclusion, domainCoRequisite,
                domainSemesterData);
    }
}

