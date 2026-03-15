package com.arriveontime.api.recommendation;

import java.time.LocalDateTime;

import com.arriveontime.application.recommendation.RecommendDepartureResult;

public record RecommendDepartureResponse(
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

    public static RecommendDepartureResponse from(RecommendDepartureResult result) {
        return new RecommendDepartureResponse(
                result.origin(),
                result.destination(),
                result.targetArrivalTime(),
                result.strict(),
                result.allowedDelayMinutes(),
                result.latestArrivalTime(),
                result.expectedTravelMinutes(),
                result.bufferMinutes(),
                result.recommendedDepartureTime()
        );
    }
}
