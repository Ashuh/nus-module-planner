package com.ashuh.nusmoduleplanner.common.domain.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.PrerequisiteTreeNode;

import java.util.List;
import java.util.Optional;

public class Module extends AbstractModule<ModuleSemesterDatum> {
    @NonNull
    private final String academicYear;
    @NonNull
    private final List<String> fulfillRequirements;
    @Nullable
    private final PrerequisiteTreeNode prerequisiteTree;

    public Module(@NonNull String moduleCode, @NonNull String title, @NonNull String description,
                  @NonNull ModuleCredit moduleCredit, @NonNull String department,
                  @NonNull String faculty, @Nullable Workload workload,
                  @NonNull String prerequisite, @NonNull String preclusion,
                  @NonNull String coRequisite, @NonNull String academicYear,
                  @NonNull List<ModuleSemesterDatum> semesterData,
                  @NonNull List<String> fulfillRequirements,
                  @Nullable PrerequisiteTreeNode prerequisiteTree) {
        super(moduleCode, title, description, moduleCredit, department, faculty, workload,
                prerequisite, preclusion, coRequisite, semesterData);
        this.academicYear = requireNonNull(academicYear);
        this.fulfillRequirements = requireNonNull(fulfillRequirements);
        this.prerequisiteTree = prerequisiteTree;
    }

    @NonNull
    public String getAcademicYear() {
        return academicYear;
    }

    @NonNull
    public List<String> getFulfillRequirements() {
        return fulfillRequirements;
    }

    @NonNull
    public Optional<PrerequisiteTreeNode> getPrerequisiteTree() {
        return Optional.ofNullable(prerequisiteTree);
    }
}
