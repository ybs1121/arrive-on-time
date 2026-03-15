package com.arriveontime.application.recommendation;

import java.util.Objects;

import com.arriveontime.application.route.RouteProvider;
import com.arriveontime.domain.arrival.ArrivalPolicy;
import com.arriveontime.domain.recommendation.DepartureRecommendation;
import com.arriveontime.domain.recommendation.RecommendationEngine;
import com.arriveontime.domain.travel.TravelDuration;

public class RecommendDepartureService implements RecommendDepartureUseCase {

    private final RouteProvider routeProvider;
    private final RecommendationEngine recommendationEngine;

    public RecommendDepartureService(RouteProvider routeProvider, RecommendationEngine recommendationEngine) {
        this.routeProvider = Objects.requireNonNull(routeProvider, "경로 제공자는 비어 있을 수 없습니다.");
        this.recommendationEngine = Objects.requireNonNull(recommendationEngine, "추천 엔진은 비어 있을 수 없습니다.");
    }

    @Override
    public RecommendDepartureResult recommend(RecommendDepartureCommand command) {
        Objects.requireNonNull(command, "추천 요청은 비어 있을 수 없습니다.");

        String origin = normalizePlace(command.origin(), "출발지는 비어 있을 수 없습니다.");
        String destination = normalizePlace(command.destination(), "도착지는 비어 있을 수 없습니다.");

        TravelDuration travelDuration = routeProvider.getExpectedTravelDuration(origin, destination);
        ArrivalPolicy arrivalPolicy = new ArrivalPolicy(
                command.targetArrivalTime(),
                command.strict(),
                command.allowedDelayMinutes()
        );
        DepartureRecommendation recommendation = recommendationEngine.recommend(arrivalPolicy, travelDuration);

        return new RecommendDepartureResult(
                origin,
                destination,
                command.targetArrivalTime(),
                command.strict(),
                command.allowedDelayMinutes(),
                recommendation.latestArrivalTime(),
                recommendation.travelDuration().toMinutes(),
                recommendation.bufferMinutes().value(),
                recommendation.recommendedDepartureTime()
        );
    }

    private String normalizePlace(String value, String errorMessage) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }

        return value.trim();
    }
}
