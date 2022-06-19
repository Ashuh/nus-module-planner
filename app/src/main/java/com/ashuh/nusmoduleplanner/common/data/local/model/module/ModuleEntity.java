package com.ashuh.nusmoduleplanner.common.data.local.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class ModuleEntity {
    @NonNull
    @PrimaryKey
    private final String moduleCode;
    @NonNull
    private final String title;
    @NonNull
    private final String description;
    private final double moduleCredit;
    @NonNull
    private final String department;
    @NonNull
    private final String faculty;
    @NonNull
    private final String prerequisite;
    @NonNull
    private final String preclusion;
    @NonNull
    private final String coRequisite;
    @NonNull
    private final String academicYear;

    public ModuleEntity(@NonNull String moduleCode, @NonNull String title,
                        @NonNull String description, double moduleCredit,
                        @NonNull String department, @NonNull String faculty,
                        @NonNull String prerequisite, @NonNull String preclusion,
                        @NonNull String coRequisite, @NonNull String academicYear) {
        requireNonNull(moduleCode);
        requireNonNull(title);
        requireNonNull(description);
        requireNonNull(department);
        requireNonNull(faculty);
        requireNonNull(prerequisite);
        requireNonNull(preclusion);
        requireNonNull(coRequisite);
        requireNonNull(academicYear);
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.moduleCredit = moduleCredit;
        this.department = department;
        this.faculty = faculty;
        this.prerequisite = prerequisite;
        this.preclusion = preclusion;
        this.coRequisite = coRequisite;
        this.academicYear = academicYear;
    }

    @NonNull
    public String getModuleCode() {
        return moduleCode;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public double getModuleCredit() {
        return moduleCredit;
    }

    @NonNull
    public String getDepartment() {
        return department;
    }

    @NonNull
    public String getFaculty() {
        return faculty;
    }

    @NonNull
    public String getPrerequisite() {
        return prerequisite;
    }

    @NonNull
    public String getPreclusion() {
        return preclusion;
    }

    @NonNull
    public String getCoRequisite() {
        return coRequisite;
    }

    @NonNull
    public String getAcademicYear() {
        return academicYear;
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleCode, title, description, moduleCredit, department, faculty,
                prerequisite, preclusion, coRequisite, academicYear);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModuleEntity that = (ModuleEntity) o;
        return Double.compare(that.moduleCredit, moduleCredit) == 0
                && moduleCode.equals(that.moduleCode)
                && title.equals(that.title)
                && description.equals(that.description)
                && department.equals(that.department)
                && faculty.equals(that.faculty)
                && prerequisite.equals(that.prerequisite)
                && preclusion.equals(that.preclusion)
                && coRequisite.equals(that.coRequisite)
                && academicYear.equals(that.academicYear);
    }

    @NonNull
    @Override
    public String toString() {
        return "ModuleEntity{"
                + "moduleCode='" + moduleCode + '\''
                + ", title='" + title + '\''
                + ", description='" + description + '\''
                + ", moduleCredit=" + moduleCredit
                + ", department='" + department + '\''
                + ", faculty='" + faculty + '\''
                + ", prerequisite='" + prerequisite + '\''
                + ", preclusion='" + preclusion + '\''
                + ", coRequisite='" + coRequisite + '\''
                + ", academicYear='" + academicYear + '\''
                + '}';
    }
}
