package com.ashuh.nusmoduleplanner.testutil.data;

import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_A;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_B;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_C;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExampleModuleCodes.MODULE_CODE_D;

import com.ashuh.nusmoduleplanner.common.data.remote.model.module.prerequisitetree.ApiAndPrerequisiteTreeNode;
import com.ashuh.nusmoduleplanner.common.data.remote.model.module.prerequisitetree.ApiOrPrerequisiteTreeNode;
import com.ashuh.nusmoduleplanner.common.data.remote.model.module.prerequisitetree.ApiSinglePrerequisiteTreeNode;

import java.util.Set;

public class ExampleApiPrerequisiteTreeNodes {
    // single prerequisites
    public static final ApiSinglePrerequisiteTreeNode API_SINGLE_PREREQUISITE_A
            = new ApiSinglePrerequisiteTreeNode(MODULE_CODE_A);
    public static final ApiSinglePrerequisiteTreeNode API_SINGLE_PREREQUISITE_B
            = new ApiSinglePrerequisiteTreeNode(MODULE_CODE_B);
    public static final ApiSinglePrerequisiteTreeNode API_SINGLE_PREREQUISITE_C
            = new ApiSinglePrerequisiteTreeNode(MODULE_CODE_C);
    public static final ApiSinglePrerequisiteTreeNode API_SINGLE_PREREQUISITE_D
            = new ApiSinglePrerequisiteTreeNode(MODULE_CODE_D);

    // logical prerequisites
    public static final ApiAndPrerequisiteTreeNode API_AND_PREREQUISITE_AB
            = new ApiAndPrerequisiteTreeNode(Set.of(API_SINGLE_PREREQUISITE_A,
            API_SINGLE_PREREQUISITE_B));

    public static final ApiOrPrerequisiteTreeNode API_OR_PREREQUISITE_CD
            = new ApiOrPrerequisiteTreeNode(Set.of(API_SINGLE_PREREQUISITE_C,
            API_SINGLE_PREREQUISITE_D));

    public static final ApiAndPrerequisiteTreeNode API_AND_PREREQUISITE_AND_AB_OR_CD
            = new ApiAndPrerequisiteTreeNode(Set.of(API_AND_PREREQUISITE_AB,
            API_OR_PREREQUISITE_CD));

    public static final ApiOrPrerequisiteTreeNode API_OR_PREREQUISITE_AND_AB_OR_CD
            = new ApiOrPrerequisiteTreeNode(Set.of(API_AND_PREREQUISITE_AB,
            API_OR_PREREQUISITE_CD));
}
