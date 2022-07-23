package com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree;

import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_A;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_B;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_C;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_D;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_E;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.OR_PREREQUISITE_AND_AB_OR_CD;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.OR_PREREQUISITE_CD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Set;

class OrPrerequisiteTreeNodeTest {

    @Test
    void isSatisfiedBy_noChildSatisfied_returnsFalse() {
        assertFalse(OR_PREREQUISITE_CD.isSatisfiedBy(Set.of(MODULE_CODE_A)));
        assertFalse(OR_PREREQUISITE_CD.isSatisfiedBy(Set.of(MODULE_CODE_B)));
        assertFalse(OR_PREREQUISITE_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B)));
        assertFalse(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A)));
        assertFalse(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_B)));
    }

    @Test
    void isSatisfiedBy_oneChildSatisfied_returnsTrue() {
        // without irrelevant modules
        assertTrue(OR_PREREQUISITE_CD.isSatisfiedBy(Set.of(MODULE_CODE_C)));
        assertTrue(OR_PREREQUISITE_CD.isSatisfiedBy(Set.of(MODULE_CODE_D)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_C)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_D)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A,
                MODULE_CODE_B)));

        // with irrelevant modules
        assertTrue(OR_PREREQUISITE_CD.isSatisfiedBy(Set.of(MODULE_CODE_C, MODULE_CODE_E)));
        assertTrue(OR_PREREQUISITE_CD.isSatisfiedBy(Set.of(MODULE_CODE_D, MODULE_CODE_E)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_C,
                MODULE_CODE_E)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_D,
                MODULE_CODE_E)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_E)));
    }

    @Test
    void isSatisfiedBy_multipleChildrenSatisfied_returnsTrue() {
        // without irrelevant modules
        assertTrue(OR_PREREQUISITE_CD.isSatisfiedBy(Set.of(MODULE_CODE_C, MODULE_CODE_D)));
        assertTrue(OR_PREREQUISITE_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_C, MODULE_CODE_D)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_C)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_D)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_C,
                MODULE_CODE_D)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_C, MODULE_CODE_D)));

        // with irrelevant modules
        assertTrue(OR_PREREQUISITE_CD.isSatisfiedBy(Set.of(MODULE_CODE_C, MODULE_CODE_D,
                MODULE_CODE_E)));
        assertTrue(OR_PREREQUISITE_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_C, MODULE_CODE_D, MODULE_CODE_E)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_C, MODULE_CODE_E)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_D, MODULE_CODE_E)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_C, MODULE_CODE_D,
                MODULE_CODE_E)));
        assertTrue(OR_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_C, MODULE_CODE_D, MODULE_CODE_E)));
    }
}
