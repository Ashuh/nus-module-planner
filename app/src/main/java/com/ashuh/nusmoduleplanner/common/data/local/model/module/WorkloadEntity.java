package com.ashuh.nusmoduleplanner.common.data.local.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Workload;

import java.util.List;
import java.util.Objects;

@Entity(
        foreignKeys = @ForeignKey(
                entity = ModuleEntity.class,
                parentColumns = "moduleCode",
                childColumns = "ownerModuleCode",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(
                value = "ownerModuleCode",
                unique = true
        )
)
public class WorkloadEntity {
    private final double lecture;
    private final double tutorial;
    private final double laboratory;
    private final double project;
    private final double preparation;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String ownerModuleCode;

    public WorkloadEntity(double lecture, double tutorial, double laboratory, double project,
                          double preparation) {
        this.lecture = lecture;
        this.tutorial = tutorial;
        this.laboratory = laboratory;
        this.project = project;
        this.preparation = preparation;
    }

    @NonNull
    public static WorkloadEntity fromDomain(Workload workload) {
        return new WorkloadEntity(workload.getLectureWorkload(),
                workload.getTutorialWorkload(),
                workload.getLaboratoryWorkload(),
                workload.getProjectWorkload(),
                workload.getPreparationWorkload());
    }

    public Workload toDomain() {
        return new Workload(List.of(lecture, tutorial, laboratory, project, preparation));
    }

    public double getLecture() {
        return lecture;
    }

    public double getTutorial() {
        return tutorial;
    }

    public String getOwnerModuleCode() {
        return ownerModuleCode;
    }

    public void setOwnerModuleCode(@NonNull String ownerModuleCode) {
        requireNonNull(ownerModuleCode);
        this.ownerModuleCode = ownerModuleCode;
    }

    public double getLaboratory() {
        return laboratory;
    }

    public double getProject() {
        return project;
    }

    public double getPreparation() {
        return preparation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lecture, tutorial, laboratory, project, preparation, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkloadEntity that = (WorkloadEntity) o;
        return Double.compare(that.lecture, lecture) == 0
                && Double.compare(that.tutorial, tutorial) == 0
                && Double.compare(that.laboratory, laboratory) == 0
                && Double.compare(that.project, project) == 0
                && Double.compare(that.preparation, preparation) == 0
                && id == that.id;
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkloadEntity{"
                + "lecture=" + lecture
                + ", tutorial=" + tutorial
                + ", laboratory=" + laboratory
                + ", project=" + project
                + ", preparation=" + preparation
                + ", id=" + id
                + '}';
    }
}
