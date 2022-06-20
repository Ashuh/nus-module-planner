package com.ashuh.nusmoduleplanner.common.domain.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import java.util.List;

public class Workload {
    private static final int VALID_LIST_SIZE = 5;
    private static final int INDEX_LECTURE = 0;
    private static final int INDEX_TUTORIAL = 1;
    private static final int INDEX_LABORATORY = 2;
    private static final int INDEX_PROJECT = 3;
    private static final int INDEX_PREPARATION = 4;

    @NonNull
    private final List<Double> workloads;

    public Workload(@NonNull List<Double> workloads) {
        requireNonNull(workloads);

        if (!isValidWorkload(workloads)) {
            throw new IllegalArgumentException("List size is not equal to " + VALID_LIST_SIZE);
        }

        this.workloads = workloads;
    }

    public static boolean isValidWorkload(List<Double> workloads) {
        return workloads.size() == VALID_LIST_SIZE;
    }

    public double getLectureWorkload() {
        return workloads.get(INDEX_LECTURE);
    }

    public double getTutorialWorkload() {
        return workloads.get(INDEX_TUTORIAL);
    }

    public double getLaboratoryWorkload() {
        return workloads.get(INDEX_LABORATORY);
    }

    public double getProjectWorkload() {
        return workloads.get(INDEX_PROJECT);
    }

    public double getPreparationWorkload() {
        return workloads.get(INDEX_PREPARATION);
    }
}
