package com.ashuh.nusmoduleplanner.common.data.remote.model.module;

import static org.junit.jupiter.api.Assertions.*;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Workload;

import org.junit.jupiter.api.Test;

import java.util.List;

class ApiWorkloadResponseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Workload(null));
    }

    @Test
    public void constructor_invalidWorkloadSize_throwsIllegalArgumentException() {
        final List<Double> workloadValuesOneEXtra = List.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);
        assertThrows(IllegalArgumentException.class, () -> new Workload(workloadValuesOneEXtra));
        final List<Double> workloadValuesMissingOne = List.of(1.0, 2.0, 3.0, 4.0);
        assertThrows(IllegalArgumentException.class, () -> new Workload(workloadValuesMissingOne));
    }

    @Test
    void toDomain_listResponse_returnsWorkload() {
        final List<Double> workloadValues = List.of(1.0, 2.0, 3.0, 4.0, 5.0);
        Workload expectedWorkload = new Workload(workloadValues);
        ApiWorkloadResponse listWorkloadResponse = new ApiWorkloadResponse(workloadValues);
        assertEquals(expectedWorkload, listWorkloadResponse.toDomain());
    }

    @Test
    void toDomain_stringResponse_returnsWorkload() {
        final List<Double> workloadValues = List.of(0.0, 0.0, 0.0, 0.0, 0.0);
        Workload expectedWorkload = new Workload(workloadValues);
        ApiWorkloadResponse stringWorkloadResponse = new ApiWorkloadResponse("string");
        assertEquals(expectedWorkload, stringWorkloadResponse.toDomain());
    }
}
