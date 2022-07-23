package com.ashuh.nusmoduleplanner.common.data.remote.model.module.prerequisitetree;

import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiPrerequisiteTreeNodes.API_AND_PREREQUISITE_AB;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiPrerequisiteTreeNodes.API_AND_PREREQUISITE_AND_AB_OR_CD;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.AND_PREREQUISITE_AB;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.AND_PREREQUISITE_AND_AB_OR_CD;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ApiAndPrerequisiteTreeNodeTest {

    @Test
    void toDomain_noLogicalChildNodes_returnsOrPrerequisiteTreeNode() {
        assertEquals(API_AND_PREREQUISITE_AB.toDomain(), AND_PREREQUISITE_AB);
    }

    @Test
    void toDomain_withLogicalChildNodes_returnsOrPrerequisiteTreeNode() {
        assertEquals(API_AND_PREREQUISITE_AND_AB_OR_CD.toDomain(), AND_PREREQUISITE_AND_AB_OR_CD);
    }
}
