package com.ashuh.nusmoduleplanner.common.data.api.model.module.weeks;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashuh.nusmoduleplanner.common.domain.model.module.Weeks;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

@JsonAdapter(ApiWeeksResponse.ApiWeeksResponseDeserializer.class)
public class ApiWeeksResponse {
    @Nullable
    private final List<Integer> listResponse;
    @Nullable
    private final ApiWeekRange weekRangeResponse;

    public ApiWeeksResponse(@NonNull List<Integer> listResponse) {
        requireNonNull(listResponse);
        this.listResponse = listResponse;
        this.weekRangeResponse = null;
    }

    public ApiWeeksResponse(@NonNull ApiWeekRange weekRangeResponse) {
        requireNonNull(weekRangeResponse);
        this.weekRangeResponse = weekRangeResponse;
        this.listResponse = null;
    }

    public Weeks toDomain() {
        if (listResponse != null) {
            return new Weeks(listResponse);
        }

        assert weekRangeResponse != null;
        LocalDate startDate = LocalDate.parse(weekRangeResponse.getStart());
        LocalDate endDate = LocalDate.parse(weekRangeResponse.getEnd());
        int interval = weekRangeResponse.getWeekInterval().orElse(0);
        List<Integer> weeks = weekRangeResponse.getWeeks();
        return new Weeks(startDate, endDate, interval, weeks);
    }

    public static class ApiWeeksResponseDeserializer implements JsonDeserializer<ApiWeeksResponse> {
        @Override
        public ApiWeeksResponse deserialize(JsonElement json, Type typeOfT,
                                            JsonDeserializationContext context)
                throws JsonParseException {
            if (json.isJsonArray()) {
                Type type = new TypeToken<List<Integer>>() {
                }.getType();
                List<Integer> weeks = context.deserialize(json, type);
                return new ApiWeeksResponse(weeks);
            }
            ApiWeekRange weekRange = context.deserialize(json, ApiWeekRange.class);
            return new ApiWeeksResponse(weekRange);
        }
    }
}
