package com.ashuh.nusmoduleplanner.common.data.api.model.module.prerequisitetree;

import androidx.annotation.NonNull;

import com.ashuh.nusmoduleplanner.common.domain.model.module.prerequisitetree.PrerequisiteTreeNode;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Type;
import java.util.Set;

@JsonAdapter(ApiPrerequisiteTreeNode.ApiPrerequisiteTreeNodeDeserializer.class)
public interface ApiPrerequisiteTreeNode {
    @NonNull
    Set<ApiPrerequisiteTreeNode> getChildren();

    @NonNull
    PrerequisiteTreeNode toDomain();

    class ApiPrerequisiteTreeNodeDeserializer implements JsonDeserializer<ApiPrerequisiteTreeNode> {
        @Override
        public ApiPrerequisiteTreeNode deserialize(JsonElement json, Type typeOfT,
                                                   JsonDeserializationContext context)
                throws JsonParseException {
            if (json.isJsonPrimitive()) {
                return new ApiSinglePrerequisiteTreeNode(json.getAsString());
            }

            JsonObject jsonObject = json.getAsJsonObject();
            if (jsonObject.has("and")) {
                return context.deserialize(jsonObject, ApiAndPrerequisiteTreeNode.class);
            }

            if (jsonObject.has("or")) {
                return context.deserialize(jsonObject, ApiOrPrerequisiteTreeNode.class);
            }

            throw new JsonParseException("Unknown prerequisite tree node type");
        }
    }
}
