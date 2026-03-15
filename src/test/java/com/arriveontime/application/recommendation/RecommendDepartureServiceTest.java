package com.arriveontime.application.recommendation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.arriveontime.application.route.RoutePoint;
import com.arriveontime.application.route.RouteProvider;
import com.arriveontime.domain.buffer.BufferPolicy;
import com.arriveontime.domain.recommendation.RecommendationEngine;
import com.arriveontime.domain.travel.TravelDuration;

class RecommendDepartureServiceTest {

    private RecommendDepartureUseCase recommendDepartureUseCase;

    @BeforeEach
    void setUp() {
        RouteProvider routeProvider = (origin, destination) -> TravelDuration.ofMinutes(25);
        RecommendationEngine recommendationEngine = new RecommendationEngine(new BufferPolicy());
        recommendDepartureUseCase = new RecommendDepartureService(routeProvider, recommendationEngine);
    }

    @Test
    void returnsRecommendationResult() {
        RecommendDepartureCommand command = new RecommendDepartureCommand(
                new RoutePoint("Jamsil Station", 37.5132612, 127.1001336),
                new RoutePoint("Gangnam Station", 37.497175, 127.027926),
                LocalDateTime.of(2026, 3, 16, 9, 0),
                true,
                0
        );

        RecommendDepartureResult result = recommendDepartureUseCase.recommend(command);

        assertThat(result.origin().name()).isEqualTo("Jamsil Station");
        assertThat(result.destination().name()).isEqualTo("Gangnam Station");
        assertThat(result.expectedTravelMinutes()).isEqualTo(25);
        assertThat(result.bufferMinutes()).isEqualTo(10);
        assertThat(result.recommendedDepartureTime()).isEqualTo(LocalDateTime.of(2026, 3, 16, 8, 25));
    }

    @Test
    void rejectsBlankPlaceName() {
        assertThatThrownBy(() -> new RecommendDepartureCommand(
                new RoutePoint("   ", 37.5132612, 127.1001336),
                new RoutePoint("Gangnam Station", 37.497175, 127.027926),
                LocalDateTime.of(2026, 3, 16, 9, 0),
                true,
                0
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("\uC7A5\uC18C \uC774\uB984\uC740 \uBE44\uC5B4 \uC788\uC744 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.");
    }
}
