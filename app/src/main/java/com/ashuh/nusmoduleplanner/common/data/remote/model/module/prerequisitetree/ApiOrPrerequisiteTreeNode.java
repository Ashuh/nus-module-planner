package com.ashuh.nusmoduleplanner.common.data.remote.model.module.prerequisitetree;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.OrPrerequisiteTreeNode;
import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.PrerequisiteTreeNode;
import com.google.gson.annotations.SerializedName;

import java.util.Set;
import java.util.stream.Collectors;

public class ApiOrPrerequisiteTreeNode implements ApiPrerequisiteTreeNode {
    @NonNull
    @SerializedName("or")
    private final Set<ApiPrerequisiteTreeNode> prerequisites;

    public ApiOrPrerequisiteTreeNode(@NonNull Set<ApiPrerequisiteTreeNode> prerequisites) {
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
        return new OrPrerequisiteTreeNode(domainPrerequisites);
    }

    @NonNull
    @Override
    public String toString() {
        return prerequisites.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" OR "));
    }
}
