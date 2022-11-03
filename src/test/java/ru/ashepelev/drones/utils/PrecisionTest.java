package ru.ashepelev.drones.utils;

import org.junit.jupiter.api.Test;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Math.pow;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.ashepelev.drones.utils.Precision.of;

class PrecisionTest {
    @Test
    void isLessThan() {
        assertThat(of(0.0).isLessThan(0.0)).isFalse();
        assertThat(of(-0.1).isLessThan(0.0)).isTrue();
        assertThat(of(-0.1).withAccuracy(1.0).isLessThan(0.0)).isFalse();
        assertThat(of(MAX_VALUE).isLessThan(0.0)).isFalse();
        assertThat(of(-MAX_VALUE).isLessThan(0.0)).isTrue();
        assertThat(of(MAX_VALUE).isLessThan(MAX_VALUE)).isFalse();
        assertThat(of(MAX_VALUE - 1000).isLessThan(MAX_VALUE)).isFalse();
        assertThat(of(MAX_VALUE - pow(10, 295)).isLessThan(MAX_VALUE)).isTrue();
    }

    @Test
    void isLessOrEqualTo() {
        assertThat(of(0.0).isLessOrEqualTo(0.0)).isTrue();
        assertThat(of(-0.1).isLessOrEqualTo(0.0)).isTrue();
        assertThat(of(-0.1).withAccuracy(1.0).isLessOrEqualTo(0.0)).isTrue();
        assertThat(of(MAX_VALUE).isLessOrEqualTo(0.0)).isFalse();
        assertThat(of(-MAX_VALUE).isLessOrEqualTo(0.0)).isTrue();
        assertThat(of(MAX_VALUE).isLessOrEqualTo(MAX_VALUE)).isTrue();
        assertThat(of(MAX_VALUE - 1000).isLessOrEqualTo(MAX_VALUE)).isTrue();
        assertThat(of(MAX_VALUE - pow(10, 295)).isLessOrEqualTo(MAX_VALUE)).isTrue();
    }

    @Test
    void isGreaterThan() {
        assertThat(of(0.0).isGreaterThan(0.0)).isFalse();
        assertThat(of(0.1).isGreaterThan(0.0)).isTrue();
        assertThat(of(0.1).withAccuracy(1.0).isGreaterThan(0.0)).isFalse();
        assertThat(of(-MAX_VALUE).isGreaterThan(0.0)).isFalse();
        assertThat(of(MAX_VALUE).isGreaterThan(0.0)).isTrue();
        assertThat(of(MAX_VALUE).isGreaterThan(MAX_VALUE)).isFalse();
        assertThat(of(MAX_VALUE).isGreaterThan(MAX_VALUE - 1000)).isFalse();
        assertThat(of(MAX_VALUE).isGreaterThan(MAX_VALUE - pow(10, 295))).isTrue();
    }

    @Test
    void isGreaterOrEqualTo() {
        assertThat(of(0.0).isGreaterOrEqualTo(0.0)).isTrue();
        assertThat(of(0.1).isGreaterOrEqualTo(0.0)).isTrue();
        assertThat(of(0.1).withAccuracy(1.0).isGreaterOrEqualTo(0.0)).isTrue();
        assertThat(of(-MAX_VALUE).isGreaterOrEqualTo(0.0)).isFalse();
        assertThat(of(MAX_VALUE).isGreaterOrEqualTo(0.0)).isTrue();
        assertThat(of(MAX_VALUE).isGreaterOrEqualTo(MAX_VALUE)).isTrue();
        assertThat(of(MAX_VALUE).isGreaterOrEqualTo(MAX_VALUE - 1000)).isTrue();
        assertThat(of(MAX_VALUE).isGreaterOrEqualTo(MAX_VALUE - pow(10, 295))).isTrue();
    }

    @Test
    void isEqualTo() {
        assertThat(of(0.0).isEqualTo(0.0)).isTrue();
        assertThat(of(0.1).isEqualTo(0.0)).isFalse();
        assertThat(of(0.1).withAccuracy(1.0).isEqualTo(0.0)).isTrue();
        assertThat(of(-MAX_VALUE).isEqualTo(0.0)).isFalse();
        assertThat(of(MAX_VALUE).isEqualTo(0.0)).isFalse();
        assertThat(of(MAX_VALUE).isEqualTo(MAX_VALUE)).isTrue();
        assertThat(of(MAX_VALUE).isEqualTo(MAX_VALUE - 1000)).isTrue();
        assertThat(of(MAX_VALUE).isEqualTo(MAX_VALUE - pow(10, 295))).isFalse();
    }
}
