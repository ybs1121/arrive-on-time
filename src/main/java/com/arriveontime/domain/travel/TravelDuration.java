package com.arriveontime.domain.travel;

import java.time.Duration;
import java.util.Objects;

public record TravelDuration(Duration value) {

    private static final long LONG_DISTANCE_THRESHOLD_MINUTES = 60L;

    public TravelDuration {
        Objects.requireNonNull(value, "예상 이동 시간은 비어 있을 수 없습니다.");

        if (value.isNegative()) {
            throw new IllegalArgumentException("예상 이동 시간은 음수일 수 없습니다.");
        }
    }

    public static TravelDuration ofMinutes(long minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("예상 이동 시간(분)은 0 이상이어야 합니다.");
        }

        return new TravelDuration(Duration.ofMinutes(minutes));
    }

    public long toMinutes() {
        return value.toMinutes();
    }

    public boolean isLongDistance() {
        return toMinutes() >= LONG_DISTANCE_THRESHOLD_MINUTES;
    }
}
