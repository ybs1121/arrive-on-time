package com.arriveontime.api.recommendation;

import com.arriveontime.application.route.RoutePoint;

public record PlaceSelectionResponse(
        String name,
        double latitude,
        double longitude
) {

    public static PlaceSelectionResponse from(RoutePoint routePoint) {
        return new PlaceSelectionResponse(
                routePoint.name(),
                routePoint.latitude(),
                routePoint.longitude()
        );
    }
}
