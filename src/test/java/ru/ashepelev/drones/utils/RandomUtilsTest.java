package ru.ashepelev.drones.utils;


import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ashepelev.drones.utils.RandomUtils.*;

class RandomUtilsTest {
    @RepeatedTest(10)
    void randomLowerLetterStringTest() {
        assertThat(randomLowerLetterString(10)).hasSize(10);
        assertThat(randomLowerLetterString(0)).isEmpty();
        assertThat(randomLowerLetterString(5)).matches("[a-z]{5}");
    }

    @RepeatedTest(10)
    void randomPositiveDoubleTest() {
        assertThat(randomPositiveDouble(5.0)).isLessThan(5.0);
        assertThat(randomPositiveDouble(5.0)).isGreaterThan(0.0);
    }

    @RepeatedTest(10)
    void randomPositiveIntegerTest() {
        assertThat(randomPositiveInteger(5)).isPositive();
        assertThat(randomPositiveInteger(5)).isLessThanOrEqualTo(5);
        var exception = assertThrows(IllegalArgumentException.class, () -> randomPositiveInteger(0));
        assertThat(exception.getMessage()).isEqualTo("Limit value must be greater than zero");
    }
}
