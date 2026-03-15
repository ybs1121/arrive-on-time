package com.arriveontime.domain.arrival;

import java.time.LocalDateTime;
import java.util.Objects;

public record ArrivalPolicy(
        LocalDateTime targetArrivalTime,
        boolean strict,
        int allowedDelayMinutes
) {

    public ArrivalPolicy {
        Objects.requireNonNull(targetArrivalTime, "목표 도착 시각은 비어 있을 수 없습니다.");

        if (allowedDelayMinutes < 0) {
            throw new IllegalArgumentException("허용 지연 시간은 0분 이상이어야 합니다.");
        }
    }

    public LocalDateTime latestArrivalTime() {
        // 엄격 도착이면 목표 시각이 그대로 마감 시각이고, 아니면 허용 지연 시간을 반영한다.
        if (strict) {
            return targetArrivalTime;
        }

        return targetArrivalTime.plusMinutes(allowedDelayMinutes);
    }

    public int effectiveAllowedDelayMinutes() {
        if (strict) {
            return 0;
        }

        return allowedDelayMinutes;
    }
}
