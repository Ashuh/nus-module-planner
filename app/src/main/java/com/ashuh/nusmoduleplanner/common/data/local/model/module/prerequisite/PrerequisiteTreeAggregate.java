package com.ashuh.nusmoduleplanner.common.data.local.model.module.prerequisite;

import static java.util.Objects.requireNonNull;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.LogicalPrerequisiteTreeNode;
import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.PrerequisiteTreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class PrerequisiteTreeAggregate {
    @NonNull
    @Embedded
    private final PrerequisiteTreeEntity tree;
    @NonNull
    @Relation(
            parentColumn = "id",
            entityColumn = "ownerId",
            entity = PrerequisiteTreeNodeEntity.class
    )
    private final List<PrerequisiteTreeNodeEntity> nodes;

    public PrerequisiteTreeAggregate(@NonNull PrerequisiteTreeEntity tree,
                                     @NonNull List<PrerequisiteTreeNodeEntity> nodes) {
        requireNonNull(tree);
        requireNonNull(nodes);
        this.tree = tree;
        this.nodes = nodes;
    }

    @NonNull
    public static PrerequisiteTreeAggregate fromDomain(@NonNull PrerequisiteTreeNode root) {
        PrerequisiteTreeEntity tree = new PrerequisiteTreeEntity();
        List<PrerequisiteTreeNodeEntity> nodeEntities = new ArrayList<>();
        Queue<PrerequisiteTreeNode> nodeFrontier = new LinkedList<>();
        Queue<Integer> parentNodeIndexFrontier = new LinkedList<>();
        parentNodeIndexFrontier.add(null);
        nodeFrontier.add(root);

        int nodeIndex = 0;
        while (!nodeFrontier.isEmpty()) {
            PrerequisiteTreeNode curNode = nodeFrontier.remove();
            Integer parentNodeIndex = parentNodeIndexFrontier.remove();
            PrerequisiteTreeNodeEntity nodeEntity = PrerequisiteTreeNodeEntity.fromDomain(curNode,
                    nodeIndex, parentNodeIndex);

            nodeEntities.add(nodeEntity);
            nodeFrontier.addAll(curNode.getChildren());
            int numChildren = curNode.getChildren().size();
            parentNodeIndexFrontier.addAll(Collections.nCopies(numChildren, nodeIndex));
            nodeIndex++;
        }
        return new PrerequisiteTreeAggregate(tree, nodeEntities);
    }

    @NonNull
    public PrerequisiteTreeNode toDomain() {
        Map<Long, PrerequisiteTreeNode> idToNode = new HashMap<>();
        Map<Long, List<Long>> parentNodeIdToChildNodeIds = new HashMap<>();
        PrerequisiteTreeNode root = null;

        for (PrerequisiteTreeNodeEntity nodeEntity : nodes) {
            PrerequisiteTreeNode node = nodeEntity.toDomain();

            if (nodeEntity.getParentNodeId() == null) {
                root = node;
            }

            idToNode.put(nodeEntity.getId(), node);
            parentNodeIdToChildNodeIds
                    .computeIfAbsent(nodeEntity.getParentNodeId(), k -> new ArrayList<>())
                    .add(nodeEntity.getId());
        }
        assert root != null;

        for (PrerequisiteTreeNodeEntity nodeEntity : nodes) {
            Long parentNodeId = nodeEntity.getParentNodeId();

            if (parentNodeId == null) {
                continue;
            }

            Log.d("adding childId ", nodeEntity.getId() + " to " + parentNodeId);
            PrerequisiteTreeNode parentNode = idToNode.get(parentNodeId);
            assert parentNode != null;
            PrerequisiteTreeNode childNode = idToNode.get(nodeEntity.getId());
            assert childNode != null;
            ((LogicalPrerequisiteTreeNode) parentNode).addChild(childNode);
        }

        return root;
    }

    @NonNull
    public PrerequisiteTreeEntity getTree() {
        return tree;
    }

    @NonNull
    public List<PrerequisiteTreeNodeEntity> getNodes() {
        return nodes;
    }

    @NonNull
    @Override
    public String toString() {
        return "PrerequisiteTreeAggregate{"
                + "tree=" + tree
                + ", nodes=" + nodes
                + '}';
    }
}
