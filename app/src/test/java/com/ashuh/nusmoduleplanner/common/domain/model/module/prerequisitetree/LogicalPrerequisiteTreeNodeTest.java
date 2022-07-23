package com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree;

import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.AND_PREREQUISITE_AB;
import static com.ashuh.nusmoduleplanner.testutil.domain.ExamplePrerequisiteTreeNodes.OR_PREREQUISITE_CD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class LogicalPrerequisiteTreeNodeTest {

    @Test
    void addChild_null_throwsNullPointerException() {
        LogicalPrerequisiteTreeNode node = new AndPrerequisiteTreeNode();
        assertThrows(NullPointerException.class, () -> node.addChild(null));
    }

    @Test
    void addChild_duplicate_notAdded() {
        Set<PrerequisiteTreeNode> children = new HashSet<>();
        children.add(AND_PREREQUISITE_AB);
        children.add(OR_PREREQUISITE_CD);
        LogicalPrerequisiteTreeNode node = new AndPrerequisiteTreeNode(children);
        children.forEach(node::addChild);
        assertEquals(node.getChildren(), children);
    }

    @Test
    void addChild_nonDuplicate_added() {
        LogicalPrerequisiteTreeNode node = new AndPrerequisiteTreeNode();
        Set<PrerequisiteTreeNode> children = Set.of(AND_PREREQUISITE_AB, OR_PREREQUISITE_CD);
        children.forEach(node::addChild);
        assertEquals(node.getChildren(), children);
    }
}
