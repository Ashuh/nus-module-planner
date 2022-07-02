package com.ashuh.nusmoduleplanner.common.data.local.model.module.prerequisite;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.AndPrerequisiteTreeNode;
import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.LogicalPrerequisiteTreeNode;
import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.OrPrerequisiteTreeNode;
import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.PrerequisiteTreeNode;
import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.SinglePrerequisiteTreeNode;

import java.util.Objects;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = PrerequisiteTreeEntity.class,
                        parentColumns = "id",
                        childColumns = "ownerId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = PrerequisiteTreeNodeEntity.class,
                        parentColumns = "id",
                        childColumns = "parentNodeId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index(
                value = {"ownerId", "treeIndex"},
                unique = true
        ),
                @Index(
                        value = "parentNodeId"
                )
        }
)
public class PrerequisiteTreeNodeEntity {
    private static final String TYPE_SINGLE = "SINGLE";
    private static final String TYPE_AND = "AND";
    private static final String TYPE_OR = "OR";

    @NonNull
    private final String type;
    @Nullable
    private final String prerequisiteModuleCode;
    private final int treeIndex;
    @Nullable
    private final Integer parentTreeIndex;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long ownerId;
    @Nullable
    private Long parentNodeId;

    @Ignore
    public PrerequisiteTreeNodeEntity(@NonNull String type, int treeIndex,
                                      @Nullable Integer parentTreeIndex) {
        this(type, null, treeIndex, parentTreeIndex);
    }

    public PrerequisiteTreeNodeEntity(@NonNull String type, @Nullable String prerequisiteModuleCode,
                                      int treeIndex, @Nullable Integer parentTreeIndex) {
        this.type = requireNonNull(type);
        this.prerequisiteModuleCode = prerequisiteModuleCode;
        this.treeIndex = treeIndex;
        this.parentTreeIndex = parentTreeIndex;
    }

    public static PrerequisiteTreeNodeEntity fromDomain(@NonNull PrerequisiteTreeNode node,
                                                        int treeIndex,
                                                        @Nullable Integer parentTreeIndex) {
        if (node instanceof SinglePrerequisiteTreeNode) {
            SinglePrerequisiteTreeNode leaf = (SinglePrerequisiteTreeNode) node;
            return new PrerequisiteTreeNodeEntity(TYPE_SINGLE, leaf.getPrerequisite(), treeIndex,
                    parentTreeIndex);
        } else {
            LogicalPrerequisiteTreeNode logicalNode = (LogicalPrerequisiteTreeNode) node;
            String type = (logicalNode instanceof AndPrerequisiteTreeNode) ? TYPE_AND : TYPE_OR;
            return new PrerequisiteTreeNodeEntity(type, treeIndex, parentTreeIndex);
        }
    }

    @NonNull
    public PrerequisiteTreeNode toDomain() {
        switch (type) {
            case TYPE_SINGLE:
                assert prerequisiteModuleCode != null;
                return new SinglePrerequisiteTreeNode(prerequisiteModuleCode);
            case TYPE_AND:
                return new AndPrerequisiteTreeNode();
            case TYPE_OR:
                return new OrPrerequisiteTreeNode();
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    public int getTreeIndex() {
        return treeIndex;
    }

    @Nullable
    public Integer getParentTreeIndex() {
        return parentTreeIndex;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @Nullable
    public Long getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(@Nullable Long parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    @Nullable
    public String getPrerequisiteModuleCode() {
        return prerequisiteModuleCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, prerequisiteModuleCode, treeIndex, parentTreeIndex, id, ownerId,
                parentNodeId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrerequisiteTreeNodeEntity that = (PrerequisiteTreeNodeEntity) o;
        return treeIndex == that.treeIndex
                && id == that.id
                && ownerId == that.ownerId
                && type.equals(that.type)
                && Objects.equals(prerequisiteModuleCode, that.prerequisiteModuleCode)
                && Objects.equals(parentTreeIndex, that.parentTreeIndex)
                && Objects.equals(parentNodeId, that.parentNodeId);
    }

    @NonNull
    @Override
    public String toString() {
        return "PrerequisiteTreeNodeEntity{"
                + "type='" + type + '\''
                + ", prerequisiteModuleCode='" + prerequisiteModuleCode + '\''
                + ", treeIndex=" + treeIndex
                + ", parentTreeIndex=" + parentTreeIndex
                + ", id=" + id
                + ", ownerId=" + ownerId
                + ", parentNodeId=" + parentNodeId
                + '}';
    }
}
