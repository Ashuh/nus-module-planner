package com.ashuh.nusmoduleplanner.data.model.nusmods.module.workload;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Optional;

public class Workload {

    List<Double> workloadList;

    String workloadString;

    public Workload(List<Double> workload) {
        workloadList = workload;
    }

    public Workload(String workload) {
        workloadString = workload;
    }

    public Optional<List<Double>> getWorkloadList() {
        return Optional.ofNullable(workloadList);
    }

    public Optional<String> getWorkloadString() {
        return Optional.ofNullable(workloadString);
    }

    @NonNull
    @Override
    public String toString() {
        return "Workload{"
                + "workloadList=" + workloadList
                + ", workloadString='" + workloadString + '\''
                + '}';
    }
}
