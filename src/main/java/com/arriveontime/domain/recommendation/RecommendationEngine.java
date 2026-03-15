package com.arriveontime.domain.recommendation;

import java.time.LocalDateTime;
import java.util.Objects;

import com.arriveontime.domain.arrival.ArrivalPolicy;
import com.arriveontime.domain.buffer.BufferMinutes;
import com.arriveontime.domain.buffer.BufferPolicy;
import com.arriveontime.domain.travel.TravelDuration;

public class RecommendationEngine {

    private final BufferPolicy bufferPolicy;

    public RecommendationEngine(BufferPolicy bufferPolicy) {
        this.bufferPolicy = Objects.requireNonNull(bufferPolicy, "여유 시간 정책은 비어 있을 수 없습니다.");
    }

    public DepartureRecommendation recommend(ArrivalPolicy arrivalPolicy, TravelDuration travelDuration) {
        Objects.requireNonNull(arrivalPolicy, "도착 정책은 비어 있을 수 없습니다.");
        Objects.requireNonNull(travelDuration, "예상 이동 시간은 비어 있을 수 없습니다.");

        LocalDateTime latestArrivalTime = arrivalPolicy.latestArrivalTime();
        BufferMinutes bufferMinutes = bufferPolicy.calculate(arrivalPolicy, travelDuration);
        // 추천 출발 시각 = 최종 도착 기준 시각 - 예상 이동 시간 - 여유 시간
        LocalDateTime recommendedDepartureTime = latestArrivalTime
                .minus(travelDuration.value())
                .minus(bufferMinutes.toDuration());

        return new DepartureRecommendation(
                arrivalPolicy,
                latestArrivalTime,
                travelDuration,
                bufferMinutes,
                recommendedDepartureTime
        );
    }
}
