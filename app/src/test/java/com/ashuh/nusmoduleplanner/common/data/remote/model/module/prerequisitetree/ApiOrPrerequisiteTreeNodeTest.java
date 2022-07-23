package com.ashuh.nusmoduleplanner.common.data.remote.model.module.prerequisitetree;

import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiPrerequisiteTreeNodes.API_AND_PREREQUISITE_AB;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiPrerequisiteTreeNodes.API_OR_PREREQUISITE_AND_AB_OR_CD;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiPrerequisiteTreeNodes.API_OR_PREREQUISITE_CD;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiPrerequisiteTreeNodes.API_SINGLE_PREREQUISITE_C;
import static com.ashuh.nusmoduleplanner.testutil.data.ExampleApiPrerequisiteTreeNodes.API_SINGLE_PREREQUISITE_D;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.OR_PREREQUISITE_AND_AB_OR_CD;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.OR_PREREQUISITE_CD;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Set;

class ApiOrPrerequisiteTreeNodeTest {

    @Test
    void getChildren_noLogicalChildNodes_returnsChildren() {
        Set<ApiPrerequisiteTreeNode> expected
                = Set.of(API_SINGLE_PREREQUISITE_C, API_SINGLE_PREREQUISITE_D);
        assertEquals(API_OR_PREREQUISITE_CD.getChildren(), expected);
    }

    @Test
    void getChildren_withLogicalChildNodes_returnsChildren() {
        Set<ApiPrerequisiteTreeNode> expected
                = Set.of(API_AND_PREREQUISITE_AB, API_OR_PREREQUISITE_CD);
        assertEquals(API_OR_PREREQUISITE_AND_AB_OR_CD.getChildren(), expected);
    }

    @Test
    void toDomain_noLogicalChildNodes_returnsOrPrerequisiteTreeNode() {
        assertEquals(API_OR_PREREQUISITE_CD.toDomain(), OR_PREREQUISITE_CD);
    }

    @Test
    void toDomain_withLogicalChildNodes_returnsOrPrerequisiteTreeNode() {
        assertEquals(API_OR_PREREQUISITE_AND_AB_OR_CD.toDomain(), OR_PREREQUISITE_AND_AB_OR_CD);
    }
}
