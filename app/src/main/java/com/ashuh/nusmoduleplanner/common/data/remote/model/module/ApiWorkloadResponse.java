package com.ashuh.nusmoduleplanner.common.data.remote.model.module;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Workload;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@JsonAdapter(ApiWorkloadResponse.ApiWorkloadResponseDeserializer.class)
public class ApiWorkloadResponse {
    @Nullable
    private final String workloadString;
    @Nullable
    private final List<Double> workloadList;

    public ApiWorkloadResponse(@NonNull String workloadString) {
        this.workloadString = requireNonNull(workloadString);
        this.workloadList = null;
    }

    public ApiWorkloadResponse(@NonNull List<Double> workloadList) {
        this.workloadList = requireNonNull(workloadList);
        this.workloadString = null;
    }

    @NonNull
    public Workload toDomain() {
        if (getType() != List.class) {
            return new Workload(List.of(0.0, 0.0, 0.0, 0.0, 0.0));
        }
        assert workloadList != null;
        return new Workload(workloadList);
    }

    private Class<?> getType() {
        if (workloadString != null) {
            return String.class;
        } else {
            return List.class;
        }
    }

    public static class ApiWorkloadResponseDeserializer
            implements JsonDeserializer<ApiWorkloadResponse> {
        @Override
        public ApiWorkloadResponse deserialize(JsonElement json, Type typeOfT,
                                               JsonDeserializationContext context)
                throws JsonParseException {
            if (json.isJsonPrimitive()) {
                return new ApiWorkloadResponse(json.getAsString());
            }
            Type type = new TypeToken<List<Double>>() {
            }.getType();
            List<Double> workload = context.deserialize(json, type);
            return new ApiWorkloadResponse(workload);
        }
    }
}
