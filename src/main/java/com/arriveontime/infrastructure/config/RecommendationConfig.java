package com.arriveontime.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.arriveontime.application.recommendation.RecommendDepartureService;
import com.arriveontime.application.recommendation.RecommendDepartureUseCase;
import com.arriveontime.application.route.RouteProvider;
import com.arriveontime.domain.buffer.BufferPolicy;
import com.arriveontime.domain.recommendation.RecommendationEngine;
import com.arriveontime.infrastructure.route.FakeRouteProvider;

@Configuration
public class RecommendationConfig {

    @Bean
    public BufferPolicy bufferPolicy() {
        return new BufferPolicy();
    }

    @Bean
    public RecommendationEngine recommendationEngine(BufferPolicy bufferPolicy) {
        return new RecommendationEngine(bufferPolicy);
    }

    @Bean
    public RouteProvider routeProvider() {
        return new FakeRouteProvider();
    }

    @Bean
    public RecommendDepartureUseCase recommendDepartureUseCase(
            RouteProvider routeProvider,
            RecommendationEngine recommendationEngine
    ) {
        return new RecommendDepartureService(routeProvider, recommendationEngine);
    }
}
