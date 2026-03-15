package com.arriveontime.domain.recommendation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.arriveontime.domain.arrival.ArrivalPolicy;
import com.arriveontime.domain.buffer.BufferPolicy;
import com.arriveontime.domain.travel.TravelDuration;

class RecommendationEngineTest {

    private RecommendationEngine recommendationEngine;

    @BeforeEach
    void setUp() {
        recommendationEngine = new RecommendationEngine(new BufferPolicy());
    }

    @Test
    void recommendsDepartureTimeForStrictArrival() {
        ArrivalPolicy arrivalPolicy = new ArrivalPolicy(
                LocalDateTime.of(2026, 3, 16, 9, 0),
                true,
                0
        );
        TravelDuration travelDuration = TravelDuration.ofMinutes(30);

        DepartureRecommendation recommendation = recommendationEngine.recommend(arrivalPolicy, travelDuration);

        assertThat(recommendation.latestArrivalTime()).isEqualTo(LocalDateTime.of(2026, 3, 16, 9, 0));
        assertThat(recommendation.bufferMinutes().value()).isEqualTo(10);
        assertThat(recommendation.recommendedDepartureTime()).isEqualTo(LocalDateTime.of(2026, 3, 16, 8, 20));
    }

    @Test
    void recommendsDepartureTimeUsingAllowedDelayForFlexibleArrival() {
        ArrivalPolicy arrivalPolicy = new ArrivalPolicy(
                LocalDateTime.of(2026, 3, 16, 9, 0),
                false,
                15
        );
        TravelDuration travelDuration = TravelDuration.ofMinutes(20);

        DepartureRecommendation recommendation = recommendationEngine.recommend(arrivalPolicy, travelDuration);

        assertThat(recommendation.latestArrivalTime()).isEqualTo(LocalDateTime.of(2026, 3, 16, 9, 15));
        assertThat(recommendation.bufferMinutes().value()).isEqualTo(3);
        assertThat(recommendation.recommendedDepartureTime()).isEqualTo(LocalDateTime.of(2026, 3, 16, 8, 52));
    }

    @Test
    void addsExtraBufferForLongDistanceTravel() {
        ArrivalPolicy arrivalPolicy = new ArrivalPolicy(
                LocalDateTime.of(2026, 3, 16, 9, 0),
                false,
                10
        );
        TravelDuration travelDuration = TravelDuration.ofMinutes(60);

        DepartureRecommendation recommendation = recommendationEngine.recommend(arrivalPolicy, travelDuration);

        assertThat(recommendation.bufferMinutes().value()).isEqualTo(8);
        assertThat(recommendation.recommendedDepartureTime()).isEqualTo(LocalDateTime.of(2026, 3, 16, 8, 2));
    }

    @Test
    void rejectsNegativeAllowedDelayMinutes() {
        assertThatThrownBy(() -> new ArrivalPolicy(
                LocalDateTime.of(2026, 3, 16, 9, 0),
                false,
                -1
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("허용 지연 시간");
    }

    @Test
    void rejectsNegativeTravelDuration() {
        assertThatThrownBy(() -> TravelDuration.ofMinutes(-5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("예상 이동 시간");
    }
}
