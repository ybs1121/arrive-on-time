package com.arriveontime.domain.recommendation;

import java.time.LocalDateTime;
import java.util.Objects;

import com.arriveontime.domain.arrival.ArrivalPolicy;
import com.arriveontime.domain.buffer.BufferMinutes;
import com.arriveontime.domain.travel.TravelDuration;

public record DepartureRecommendation(
        ArrivalPolicy arrivalPolicy,
        LocalDateTime latestArrivalTime,
        TravelDuration travelDuration,
        BufferMinutes bufferMinutes,
        LocalDateTime recommendedDepartureTime
) {

    public DepartureRecommendation {
        Objects.requireNonNull(arrivalPolicy, "도착 정책은 비어 있을 수 없습니다.");
        Objects.requireNonNull(latestArrivalTime, "최종 도착 기준 시각은 비어 있을 수 없습니다.");
        Objects.requireNonNull(travelDuration, "예상 이동 시간은 비어 있을 수 없습니다.");
        Objects.requireNonNull(bufferMinutes, "여유 시간은 비어 있을 수 없습니다.");
        Objects.requireNonNull(recommendedDepartureTime, "추천 출발 시각은 비어 있을 수 없습니다.");
    }
}
