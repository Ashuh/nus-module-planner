package com.ashuh.nusmoduleplanner.common.data.api.model.module.prerequisitetree;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.AndPrerequisiteTreeNode;
import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.PrerequisiteTreeNode;
import com.google.gson.annotations.SerializedName;

import java.util.Set;
import java.util.stream.Collectors;

public class ApiAndPrerequisiteTreeNode implements ApiPrerequisiteTreeNode {
    @NonNull
    @SerializedName("and")
    private final Set<ApiPrerequisiteTreeNode> prerequisites;

    public ApiAndPrerequisiteTreeNode(@NonNull Set<ApiPrerequisiteTreeNode> prerequisites) {
        requireNonNull(prerequisites);
        this.prerequisites = prerequisites;
    }

    @NonNull
    @Override
    public Set<ApiPrerequisiteTreeNode> getChildren() {
        return prerequisites;
    }

    @NonNull
    @Override
    public PrerequisiteTreeNode toDomain() {
        Set<PrerequisiteTreeNode> domainPrerequisites = prerequisites.stream()
                .map(ApiPrerequisiteTreeNode::toDomain)
                .collect(Collectors.toSet());
        return new AndPrerequisiteTreeNode(domainPrerequisites);
    }

    @NonNull
    @Override
    public String toString() {
        return prerequisites.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" AND "));
    }
}
