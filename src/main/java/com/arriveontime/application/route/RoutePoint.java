package com.arriveontime.application.route;

public record RoutePoint(
        String name,
        double latitude,
        double longitude
) {

    public RoutePoint {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("장소 이름은 비어 있을 수 없습니다.");
        }

        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("위도는 -90 이상 90 이하여야 합니다.");
        }

        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("경도는 -180 이상 180 이하여야 합니다.");
        }

        name = name.trim();
    }
}
