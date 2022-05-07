package com.ashuh.nusmoduleplanner.data.model.nusmods.module;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.data.model.nusmods.module.prereqtree.PrereqTree;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.ModuleInformationSemesterDatum;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.ModuleSemesterDatum;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.semesterdatum.SemesterType;
import com.ashuh.nusmoduleplanner.data.model.nusmods.module.workload.Workload;
import com.ashuh.nusmoduleplanner.data.model.timetable.AssignedModule;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Module extends BaseDetailedModule {

    private final List<ModuleSemesterDatum> semesterData;
    private final PrereqTree prereqTree;
    private final List<String> fulfillRequirements;

    public Module(@NonNull String moduleCode, @NonNull String title, @NonNull String acadYear,
                  @NonNull String description, @NonNull String faculty, @NonNull String department,
                  double moduleCredit, @NonNull Workload workload, String preclusion,
                  String prerequisite, String corequisite, List<ModuleSemesterDatum> semesterData,
                  PrereqTree prereqTree, List<String> fulfillRequirements) {
        super(moduleCode, title, acadYear, description, faculty, department, moduleCredit, workload,
                preclusion, prerequisite, corequisite);
        this.semesterData = semesterData;
        this.prereqTree = prereqTree;
        this.fulfillRequirements = fulfillRequirements;
    }

    public Optional<Set<SemesterType>> getSemesters() {
        if (!hasSemesters()) {
            return Optional.empty();
        }

        return Optional.of(semesterData.stream().map(ModuleInformationSemesterDatum::getSemester)
                .collect(Collectors.toSet()));
    }

    public boolean hasSemesters() {
        return semesterData != null && !semesterData.isEmpty();
    }

    public List<ModuleSemesterDatum> getSemesterData() {
        return semesterData;
    }

    public List<String> getFulfillRequirements() {
        return fulfillRequirements;
    }

    public PrereqTree getPrereqTree() {
        return prereqTree;
    }

    public AssignedModule toAssignedModule(SemesterType semester) {
        ModuleSemesterDatum semesterDatum =
                getSemesterDatum(semester).orElseThrow(() -> new IllegalArgumentException(
                        "Module is not offered in " + semester.toString()));
        return new AssignedModule(moduleCode, title, moduleCredit, semester, semesterDatum);
    }

    public Optional<ModuleSemesterDatum> getSemesterDatum(SemesterType semType) {
        for (ModuleSemesterDatum datum : semesterData) {
            if (datum.getSemester() == semType) {
                return Optional.of(datum);
            }
        }

        return Optional.empty();
    }

    @NonNull
    @Override
    public String toString() {
        return "Module{" + "acadYear='" + acadYear + '\''
                + ", description='" + description + '\''
                + ", faculty='" + faculty + '\''
                + ", department='" + department + '\''
                + ", moduleCredit=" + moduleCredit
                + ", preclusion='" + preclusion + '\''
                + ", prerequisite='" + prerequisite + '\''
                + ", corequisite='" + corequisite + '\''
                + ", workload=" + workload
                + ", moduleCode='" + moduleCode + '\''
                + ", title='" + title + '\''
                + ", semesterData=" + semesterData
                + ", prereqTree=" + prereqTree
                + '}';
    }
}
