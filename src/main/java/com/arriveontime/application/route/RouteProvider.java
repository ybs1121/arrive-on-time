package com.arriveontime.application.route;

import com.arriveontime.domain.travel.TravelDuration;

public interface RouteProvider {

    TravelDuration getExpectedTravelDuration(RoutePoint origin, RoutePoint destination);
}
