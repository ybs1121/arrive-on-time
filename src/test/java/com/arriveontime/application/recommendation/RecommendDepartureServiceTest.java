package com.arriveontime.application.recommendation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void 추천_결과를_정상적으로_반환한다() {
        RecommendDepartureCommand command = new RecommendDepartureCommand(
                "잠실역",
                "강남역",
                LocalDateTime.of(2026, 3, 16, 9, 0),
                true,
                0
        );

        RecommendDepartureResult result = recommendDepartureUseCase.recommend(command);

        assertThat(result.origin()).isEqualTo("잠실역");
        assertThat(result.destination()).isEqualTo("강남역");
        assertThat(result.expectedTravelMinutes()).isEqualTo(25);
        assertThat(result.bufferMinutes()).isEqualTo(10);
        assertThat(result.recommendedDepartureTime()).isEqualTo(LocalDateTime.of(2026, 3, 16, 8, 25));
    }

    @Test
    void 공백_출발지는_예외가_발생한다() {
        RecommendDepartureCommand command = new RecommendDepartureCommand(
                "   ",
                "강남역",
                LocalDateTime.of(2026, 3, 16, 9, 0),
                true,
                0
        );

        assertThatThrownBy(() -> recommendDepartureUseCase.recommend(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출발지는 비어 있을 수 없습니다.");
    }
}
