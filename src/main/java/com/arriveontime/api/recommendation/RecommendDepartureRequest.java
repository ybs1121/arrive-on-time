package com.arriveontime.api.recommendation;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import com.arriveontime.application.recommendation.RecommendDepartureCommand;

public record RecommendDepartureRequest(
        @NotNull(message = "출발지는 비어 있을 수 없습니다.")
        @Valid
        PlaceSelectionRequest origin,

        @NotNull(message = "도착지는 비어 있을 수 없습니다.")
        @Valid
        PlaceSelectionRequest destination,

        @NotNull(message = "목표 도착 시각은 비어 있을 수 없습니다.")
        LocalDateTime targetArrivalTime,

        @NotNull(message = "도착 엄수 여부는 비어 있을 수 없습니다.")
        Boolean strict,

        @NotNull(message = "허용 지연 시간은 비어 있을 수 없습니다.")
        @Min(value = 0, message = "허용 지연 시간은 0분 이상이어야 합니다.")
        Integer allowedDelayMinutes
) {

    public RecommendDepartureCommand toCommand() {
        return new RecommendDepartureCommand(
                origin.toRoutePoint(),
                destination.toRoutePoint(),
                targetArrivalTime,
                strict,
                allowedDelayMinutes
        );
    }
}
