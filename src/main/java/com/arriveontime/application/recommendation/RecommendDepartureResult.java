package com.arriveontime.application.recommendation;

import java.time.LocalDateTime;
import java.util.Objects;

public record RecommendDepartureResult(
        String origin,
        String destination,
        LocalDateTime targetArrivalTime,
        boolean strict,
        int allowedDelayMinutes,
        LocalDateTime latestArrivalTime,
        long expectedTravelMinutes,
        int bufferMinutes,
        LocalDateTime recommendedDepartureTime
) {

    public RecommendDepartureResult {
        Objects.requireNonNull(origin, "출발지는 비어 있을 수 없습니다.");
        Objects.requireNonNull(destination, "도착지는 비어 있을 수 없습니다.");
        Objects.requireNonNull(targetArrivalTime, "목표 도착 시각은 비어 있을 수 없습니다.");
        Objects.requireNonNull(latestArrivalTime, "최종 도착 기준 시각은 비어 있을 수 없습니다.");
        Objects.requireNonNull(recommendedDepartureTime, "추천 출발 시각은 비어 있을 수 없습니다.");
    }
}
