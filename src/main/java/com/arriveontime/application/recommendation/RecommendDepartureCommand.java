package com.arriveontime.application.recommendation;

import java.time.LocalDateTime;
import java.util.Objects;

import com.arriveontime.application.route.RoutePoint;

public record RecommendDepartureCommand(
        RoutePoint origin,
        RoutePoint destination,
        LocalDateTime targetArrivalTime,
        boolean strict,
        int allowedDelayMinutes
) {

    public RecommendDepartureCommand {
        Objects.requireNonNull(origin, "출발지는 비어 있을 수 없습니다.");
        Objects.requireNonNull(destination, "도착지는 비어 있을 수 없습니다.");
        Objects.requireNonNull(targetArrivalTime, "목표 도착 시각은 비어 있을 수 없습니다.");

        if (allowedDelayMinutes < 0) {
            throw new IllegalArgumentException("허용 지연 시간은 0분 이상이어야 합니다.");
        }
    }
}
