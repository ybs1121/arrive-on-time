package com.arriveontime.domain.buffer;

import java.time.Duration;

public record BufferMinutes(int value) {

    public BufferMinutes {
        if (value < 0) {
            throw new IllegalArgumentException("여유 시간은 0분 이상이어야 합니다.");
        }
    }

    public static BufferMinutes of(int minutes) {
        return new BufferMinutes(minutes);
    }

    public Duration toDuration() {
        return Duration.ofMinutes(value);
    }

    public BufferMinutes plus(BufferMinutes other) {
        return new BufferMinutes(this.value + other.value);
    }
}
