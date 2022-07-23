package com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree;

import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_A;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_B;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_C;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_D;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_E;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.AND_PREREQUISITE_AB;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.AND_PREREQUISITE_AND_AB_OR_CD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Set;

class AndPrerequisiteTreeNodeTest {

    @Test
    void isSatisfiedBy_noChildSatisfied_returnsFalse() {
        assertFalse(AND_PREREQUISITE_AB.isSatisfiedBy(Set.of(MODULE_CODE_C)));
        assertFalse(AND_PREREQUISITE_AB.isSatisfiedBy(Set.of(MODULE_CODE_D)));

        assertFalse(AND_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A)));
        assertFalse(AND_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_B)));
    }

    @Test
    void isSatisfiedBy_someChildrenSatisfied_returnsFalse() {
        assertFalse(AND_PREREQUISITE_AB.isSatisfiedBy(Set.of(MODULE_CODE_A)));
        assertFalse(AND_PREREQUISITE_AB.isSatisfiedBy(Set.of(MODULE_CODE_B)));

        assertFalse(AND_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_C)));
        assertFalse(AND_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_D)));
        assertFalse(AND_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_C,
                MODULE_CODE_D)));
        assertFalse(AND_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A,
                MODULE_CODE_B)));
    }

    @Test
    void isSatisfiedBy_allChildrenSatisfied_returnsTrue() {
        // without irrelevant modules
        assertTrue(AND_PREREQUISITE_AB.isSatisfiedBy(Set.of(MODULE_CODE_A,MODULE_CODE_B)));
        assertTrue(AND_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_C)));
        assertTrue(AND_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_D)));
        assertTrue(AND_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_C, MODULE_CODE_D)));

        // with irrelevant modules
        assertTrue(AND_PREREQUISITE_AB.isSatisfiedBy(Set.of(MODULE_CODE_A,MODULE_CODE_B,
                MODULE_CODE_E)));
        assertTrue(AND_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_C, MODULE_CODE_E)));
        assertTrue(AND_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_D, MODULE_CODE_E)));
        assertTrue(AND_PREREQUISITE_AND_AB_OR_CD.isSatisfiedBy(Set.of(MODULE_CODE_A, MODULE_CODE_B,
                MODULE_CODE_C, MODULE_CODE_D, MODULE_CODE_E)));
    }
}
