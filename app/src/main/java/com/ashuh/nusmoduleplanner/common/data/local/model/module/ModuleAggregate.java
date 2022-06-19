package com.ashuh.nusmoduleplanner.common.data.local.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.ashuh.nusmoduleplanner.common.data.local.model.module.prerequisite.PrerequisiteTreeAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.prerequisite.PrerequisiteTreeEntity;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.semesterdatum.SemesterDatumAggregate;
import com.ashuh.nusmoduleplanner.common.data.local.model.module.semesterdatum.SemesterDatumEntity;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Module;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleCredit;
import com.ashuh.nusmoduleplanner.common.domain.model.module.ModuleSemesterDatum;
import com.ashuh.nusmoduleplanner.common.domain.model.module.Workload;
import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.PrerequisiteTreeNode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModuleAggregate {
    @NonNull
    @Embedded
    private final ModuleEntity module;
    @NonNull
    @Relation(
            parentColumn = "moduleCode",
            entityColumn = "ownerModuleCode",
            entity = SemesterDatumEntity.class
    )
    private final List<SemesterDatumAggregate> semesterDatumAggregates;
    @Nullable
    @Relation(
            parentColumn = "moduleCode",
            entityColumn = "ownerModuleCode",
            entity = WorkloadEntity.class
    )
    private final WorkloadEntity workload;
    @Nullable
    @Relation(
            parentColumn = "moduleCode",
            entityColumn = "ownerModuleCode",
            entity = PrerequisiteTreeEntity.class
    )
    private final PrerequisiteTreeAggregate prerequisiteTreeAggregate;
    @NonNull
    @Relation(
            parentColumn = "moduleCode",
            entityColumn = "moduleCode",
            entity = ModuleEntity.class,
            associateBy = @Junction(
                    value = ModuleRequirementCrossRefEntity.class,
                    parentColumn = "fulfillsModuleCode",
                    entityColumn = "fulfilledModuleCode"
            )
    )
    private final List<String> fulfillRequirements;

    public ModuleAggregate(@NonNull ModuleEntity module,
                           @NonNull List<SemesterDatumAggregate> semesterDatumAggregates,
                           @Nullable WorkloadEntity workload,
                           @Nullable PrerequisiteTreeAggregate prerequisiteTreeAggregate,
                           @NonNull List<String> fulfillRequirements) {
        requireNonNull(module);
        requireNonNull(semesterDatumAggregates);
        requireNonNull(fulfillRequirements);
        this.module = module;
        this.semesterDatumAggregates = semesterDatumAggregates;
        this.workload = workload;
        this.prerequisiteTreeAggregate = prerequisiteTreeAggregate;
        this.fulfillRequirements = fulfillRequirements;
    }

    public static ModuleAggregate fromDomain(@NonNull Module module) {
        String moduleCode = module.getModuleCode();
        String title = module.getTitle();
        String description = module.getDescription();
        double moduleCredit = module.getModuleCredit().getValue();
        String department = module.getDepartment();
        String faculty = module.getFaculty();
        String prerequisite = module.getPrerequisite();
        String preclusion = module.getPreclusion();
        String coRequisite = module.getCoRequisite();
        String academicYear = module.getAcademicYear();
        ModuleEntity moduleEntity = new ModuleEntity(moduleCode, title, description, moduleCredit,
                department, faculty, prerequisite, preclusion, coRequisite, academicYear);

        List<SemesterDatumAggregate> semesterDatumAggregates = module.getSemesterData().stream()
                .map(SemesterDatumAggregate::fromDomain)
                .collect(Collectors.toList());

        WorkloadEntity workload = module.getWorkload()
                .map(WorkloadEntity::fromDomain)
                .orElse(null);

        PrerequisiteTreeAggregate prerequisiteTreeAggregate = module.getPrerequisiteTree()
                .map(PrerequisiteTreeAggregate::fromDomain)
                .orElse(null);

        List<String> fulfillRequirements = module.getFulfillRequirements();

        return new ModuleAggregate(moduleEntity, semesterDatumAggregates, workload,
                prerequisiteTreeAggregate, fulfillRequirements);
    }

    @NonNull
    public Module toDomain() {
        String moduleCode = module.getModuleCode();
        String title = module.getTitle();
        String description = module.getDescription();
        ModuleCredit moduleCredit = new ModuleCredit(module.getModuleCredit());
        String department = module.getDepartment();
        String faculty = module.getFaculty();
        Workload domainWorkload = getWorkload()
                .map(WorkloadEntity::toDomain)
                .orElse(null);
        String prerequisite = module.getPrerequisite();
        String preclusion = module.getPreclusion();
        String coRequisite = module.getCoRequisite();
        String academicYear = module.getAcademicYear();
        List<ModuleSemesterDatum> domainSemesterData = semesterDatumAggregates.stream()
                .map(SemesterDatumAggregate::toDomain)
                .collect(Collectors.toList());
        PrerequisiteTreeNode domainPrerequisiteTree = getPrerequisiteTreeAggregate()
                .map(PrerequisiteTreeAggregate::toDomain)
                .orElse(null);

        return new Module(moduleCode, title, description, moduleCredit, department, faculty,
                domainWorkload, prerequisite, preclusion, coRequisite, academicYear,
                domainSemesterData, fulfillRequirements, domainPrerequisiteTree);
    }

    @NonNull
    public Optional<WorkloadEntity> getWorkload() {
        return Optional.ofNullable(workload);
    }

    @NonNull
    public Optional<PrerequisiteTreeAggregate> getPrerequisiteTreeAggregate() {
        return Optional.ofNullable(prerequisiteTreeAggregate);
    }

    @NonNull
    public ModuleEntity getModule() {
        return module;
    }

    @NonNull
    public List<SemesterDatumAggregate> getSemesterDatumAggregates() {
        return semesterDatumAggregates;
    }

    @NonNull
    public List<String> getFulfillRequirements() {
        return fulfillRequirements;
    }

    @NonNull
    @Override
    public String toString() {
        return "ModuleAggregate{"
                + "module=" + module
                + ", semesterDatumAggregates=" + semesterDatumAggregates
                + ", prerequisiteTreeAggregate=" + prerequisiteTreeAggregate
                + '}';
    }
}

