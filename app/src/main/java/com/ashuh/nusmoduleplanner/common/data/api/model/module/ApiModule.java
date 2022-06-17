package com.ashuh.nusmoduleplanner.common.data.api.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashuh.nusmoduleplanner.common.data.api.model.module.prerequisitetree.ApiPrerequisiteTreeNode;
import com.ashuh.nusmoduleplanner.common.data.api.model.module.semesterdatum.ApiModuleSemesterDatum;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleCredit;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleSemesterDatum;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Workload;
import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.PrerequisiteTreeNode;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ApiModule extends ApiBaseDetailedModule {
    @NonNull
    @SerializedName("acadYear")
    private final String acadYear;
    @NonNull
    @SerializedName("semesterData")
    private final List<ApiModuleSemesterDatum> semesterData;
    @Nullable
    @SerializedName("prereqTree")
    private final ApiPrerequisiteTreeNode prerequisiteTree;
    @Nullable
    @SerializedName("fulfillRequirements")
    private final List<String> fulfillRequirements;

    public ApiModule(String moduleCode, String title, String description, String faculty,
                     String department, ApiWorkloadResponse workload, String preclusion,
                     String prerequisite, String coRequisite, double moduleCredit,
                     @NonNull String acadYear, @NonNull List<ApiModuleSemesterDatum> semesterData,
                     @Nullable ApiPrerequisiteTreeNode prerequisiteTree,
                     @Nullable List<String> fulfillRequirements) {
        super(moduleCode, title, description, faculty, department, workload, preclusion,
                prerequisite, coRequisite, moduleCredit);
        requireNonNull(acadYear);
        requireNonNull(semesterData);
        this.acadYear = acadYear;
        this.semesterData = semesterData;
        this.prerequisiteTree = prerequisiteTree;
        this.fulfillRequirements = fulfillRequirements;
    }

    @NonNull
    public String getAcadYear() {
        return acadYear;
    }

    @NonNull
    public List<ApiModuleSemesterDatum> getSemesterData() {
        return semesterData;
    }

    @NonNull
    public Module toDomain() {
        Workload domainWorkload = getWorkload()
                .map(ApiWorkloadResponse::toDomain)
                .orElse(null);
        String domainPreclusion = getPreclusion();
        String domainPrerequisite = getPrerequisite();
        String domainCoRequisite = getCoRequisite();
        ModuleCredit domainModuleCredit = new ModuleCredit(getModuleCredit());
        PrerequisiteTreeNode prerequisiteTreeNode = getPrerequisiteTree()
                .map(ApiPrerequisiteTreeNode::toDomain)
                .orElse(null);

        List<ModuleSemesterDatum> domainSemesterData = semesterData.stream()
                .map(ApiModuleSemesterDatum::toDomain)
                .collect(Collectors.toList());

        List<String> fulfillRequirements = getFulfillRequirements();

        return new Module(moduleCode, title, description, domainModuleCredit, department, faculty,
                domainWorkload, domainPrerequisite, domainPreclusion, domainCoRequisite, acadYear,
                domainSemesterData, fulfillRequirements, prerequisiteTreeNode);
    }

    @NonNull
    public Optional<ApiPrerequisiteTreeNode> getPrerequisiteTree() {
        return Optional.ofNullable(prerequisiteTree);
    }

    @NonNull
    public List<String> getFulfillRequirements() {
        return Objects.requireNonNullElse(fulfillRequirements, Collections.emptyList());
    }
}
