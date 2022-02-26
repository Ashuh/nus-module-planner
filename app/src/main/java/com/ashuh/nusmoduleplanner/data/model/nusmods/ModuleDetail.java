package com.ashuh.nusmoduleplanner.data.model.nusmods;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class ModuleDetail extends Module {
    @SerializedName("semesterData")
    protected final List<SemesterDetail> detailedSemesters;

    public ModuleDetail(@NonNull String moduleCode, String title, String department,
                        String faculty, double credit, List<SemesterDetail> detailedSemesters,
                        String description, String prerequisite, String preclusion,
                        String corequisite) {
        super(moduleCode, title, description, credit, department, faculty, prerequisite, preclusion,
                corequisite);
        this.detailedSemesters = detailedSemesters;
    }

    public List<SemesterDetail> getDetailedSemesters() {
        return detailedSemesters;
    }

    public SemesterDetail getSemesterDetail(Semester semester) {

        for (SemesterDetail data : detailedSemesters) {
            if (data.getSemester() == semester) {
                return data;
            }
        }

        throw new IllegalArgumentException("Data for " + semester.toString() + " not available");
    }

    @NonNull
    @Override
    public String toString() {
        return moduleCode + " " + title;
    }
}
