package com.ashuh.nusmoduleplanner.testutil.domain;

import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_A;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_B;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_C;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_D;

import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.AndPrerequisiteTreeNode;
import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.OrPrerequisiteTreeNode;
import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.SinglePrerequisiteTreeNode;

import java.util.Set;

public class ExamplePrerequisiteTreeNodes {
    // single prerequisites
    public static final SinglePrerequisiteTreeNode SINGLE_PREREQUISITE_A
            = new SinglePrerequisiteTreeNode(MODULE_CODE_A);
    public static final SinglePrerequisiteTreeNode SINGLE_PREREQUISITE_B
            = new SinglePrerequisiteTreeNode(MODULE_CODE_B);
    public static final SinglePrerequisiteTreeNode SINGLE_PREREQUISITE_C
            = new SinglePrerequisiteTreeNode(MODULE_CODE_C);
    public static final SinglePrerequisiteTreeNode SINGLE_PREREQUISITE_D
            = new SinglePrerequisiteTreeNode(MODULE_CODE_D);

    // logical prerequisites
    public static final AndPrerequisiteTreeNode AND_PREREQUISITE_AB
            = new AndPrerequisiteTreeNode(Set.of(SINGLE_PREREQUISITE_A, SINGLE_PREREQUISITE_B));

    public static final OrPrerequisiteTreeNode OR_PREREQUISITE_CD
            = new OrPrerequisiteTreeNode(Set.of(SINGLE_PREREQUISITE_C, SINGLE_PREREQUISITE_D));

    public static final AndPrerequisiteTreeNode AND_PREREQUISITE_AND_AB_OR_CD
            = new AndPrerequisiteTreeNode(Set.of(AND_PREREQUISITE_AB, OR_PREREQUISITE_CD));

    public static final OrPrerequisiteTreeNode OR_PREREQUISITE_AND_AB_OR_CD
            = new OrPrerequisiteTreeNode(Set.of(AND_PREREQUISITE_AB, OR_PREREQUISITE_CD));
}
