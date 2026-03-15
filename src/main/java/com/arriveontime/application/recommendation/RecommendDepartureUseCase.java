package com.arriveontime.application.recommendation;

public interface RecommendDepartureUseCase {

    RecommendDepartureResult recommend(RecommendDepartureCommand command);
}
