package com.arriveontime.infrastructure.route;

import com.arriveontime.application.route.RoutePoint;
import com.arriveontime.application.route.RouteProvider;
import com.arriveontime.domain.travel.TravelDuration;

public class FakeRouteProvider implements RouteProvider {

    @Override
    public TravelDuration getExpectedTravelDuration(RoutePoint origin, RoutePoint destination) {
        if (origin.name().equalsIgnoreCase(destination.name())
                && origin.latitude() == destination.latitude()
                && origin.longitude() == destination.longitude()) {
            return TravelDuration.ofMinutes(5);
        }

        // 실제 길찾기 API를 붙이기 전까지는 두 좌표 사이의 직선 거리를 기준으로 가짜 이동 시간을 계산한다.
        double distanceInKilometers = calculateDistanceInKilometers(origin, destination);
        int estimatedMinutes = Math.max(8, (int) Math.round(distanceInKilometers * 3.4 + 6));

        return TravelDuration.ofMinutes(estimatedMinutes);
    }

    private double calculateDistanceInKilometers(RoutePoint origin, RoutePoint destination) {
        double earthRadius = 6371.0;
        double latitudeDelta = Math.toRadians(destination.latitude() - origin.latitude());
        double longitudeDelta = Math.toRadians(destination.longitude() - origin.longitude());
        double startLatitude = Math.toRadians(origin.latitude());
        double endLatitude = Math.toRadians(destination.latitude());

        double haversine = Math.sin(latitudeDelta / 2) * Math.sin(latitudeDelta / 2)
                + Math.cos(startLatitude) * Math.cos(endLatitude)
                * Math.sin(longitudeDelta / 2) * Math.sin(longitudeDelta / 2);

        double centralAngle = 2 * Math.atan2(Math.sqrt(haversine), Math.sqrt(1 - haversine));

        return earthRadius * centralAngle;
    }
}
