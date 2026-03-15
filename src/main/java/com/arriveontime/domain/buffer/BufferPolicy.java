package com.arriveontime.domain.buffer;

import com.arriveontime.domain.arrival.ArrivalPolicy;
import com.arriveontime.domain.travel.TravelDuration;

public class BufferPolicy {

    private static final int STRICT_BASE_BUFFER_MINUTES = 10;
    private static final int FLEXIBLE_BASE_BUFFER_MINUTES = 3;
    private static final int LONG_DISTANCE_EXTRA_BUFFER_MINUTES = 5;

    public BufferMinutes calculate(ArrivalPolicy arrivalPolicy, TravelDuration travelDuration) {
        // 문서 기준 규칙:
        // 엄격 도착이면 10분, 여유 도착이면 3분, 60분 이상 이동이면 5분을 추가한다.
        int bufferMinutes = arrivalPolicy.strict()
                ? STRICT_BASE_BUFFER_MINUTES
                : FLEXIBLE_BASE_BUFFER_MINUTES;

        if (travelDuration.isLongDistance()) {
            bufferMinutes += LONG_DISTANCE_EXTRA_BUFFER_MINUTES;
        }

        return BufferMinutes.of(bufferMinutes);
    }
}
