package com.ashuh.nusmoduleplanner.common.data.remote.model.module.prerequisitetree;

import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiPrerequisiteTreeNodes.API_SINGLE_PREREQUISITE_A;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiPrerequisiteTreeNodes.API_SINGLE_PREREQUISITE_B;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiPrerequisiteTreeNodes.API_SINGLE_PREREQUISITE_C;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiPrerequisiteTreeNodes.API_SINGLE_PREREQUISITE_D;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_A;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_B;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_C;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_D;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.SINGLE_PREREQUISITE_A;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.SINGLE_PREREQUISITE_B;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.SINGLE_PREREQUISITE_C;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.SINGLE_PREREQUISITE_D;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ApiSinglePrerequisiteTreeNodeTest {

    @Test
    void getPrerequisite() {
        assertEquals(API_SINGLE_PREREQUISITE_A.getPrerequisite(), MODULE_CODE_A);
        assertEquals(API_SINGLE_PREREQUISITE_B.getPrerequisite(), MODULE_CODE_B);
        assertEquals(API_SINGLE_PREREQUISITE_C.getPrerequisite(), MODULE_CODE_C);
        assertEquals(API_SINGLE_PREREQUISITE_D.getPrerequisite(), MODULE_CODE_D);
    }

    @Test
    void getChildren() {
        assertTrue(API_SINGLE_PREREQUISITE_A.getChildren().isEmpty());
        assertTrue(API_SINGLE_PREREQUISITE_B.getChildren().isEmpty());
        assertTrue(API_SINGLE_PREREQUISITE_C.getChildren().isEmpty());
        assertTrue(API_SINGLE_PREREQUISITE_D.getChildren().isEmpty());
    }

    @Test
    void toDomain() {
        assertEquals(API_SINGLE_PREREQUISITE_A.toDomain(), SINGLE_PREREQUISITE_A);
        assertEquals(API_SINGLE_PREREQUISITE_B.toDomain(), SINGLE_PREREQUISITE_B);
        assertEquals(API_SINGLE_PREREQUISITE_C.toDomain(), SINGLE_PREREQUISITE_C);
        assertEquals(API_SINGLE_PREREQUISITE_D.toDomain(), SINGLE_PREREQUISITE_D);
    }
}
