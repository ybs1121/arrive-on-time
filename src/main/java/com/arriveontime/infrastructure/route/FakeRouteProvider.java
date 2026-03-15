package com.arriveontime.infrastructure.route;

import com.arriveontime.application.route.RouteProvider;
import com.arriveontime.domain.travel.TravelDuration;

public class FakeRouteProvider implements RouteProvider {

    @Override
    public TravelDuration getExpectedTravelDuration(String origin, String destination) {
        String normalizedOrigin = normalizePlace(origin, "출발지는 비어 있을 수 없습니다.");
        String normalizedDestination = normalizePlace(destination, "도착지는 비어 있을 수 없습니다.");

        if (normalizedOrigin.equalsIgnoreCase(normalizedDestination)) {
            return TravelDuration.ofMinutes(5);
        }

        // 실제 길찾기 API를 붙이기 전까지는 입력값에 따라 재현 가능한 가짜 이동 시간을 반환한다.
        int seed = normalizedOrigin.length() * 7 + normalizedDestination.length() * 5;
        int estimatedMinutes = 20 + (seed % 41);

        return TravelDuration.ofMinutes(estimatedMinutes);
    }

    private String normalizePlace(String value, String errorMessage) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }

        return value.trim();
    }
}
