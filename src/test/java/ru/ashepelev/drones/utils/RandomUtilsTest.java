package ru.ashepelev.drones.utils;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ashepelev.drones.utils.RandomUtils.randomPositiveDouble;
import static ru.ashepelev.drones.utils.RandomUtils.randomLowerLetterString;

class RandomUtilsTest {
    @Test
    void randomLowerLetterStringTest() {
        assertThat(randomLowerLetterString(10).length()).isEqualTo(10);
        assertThat(randomLowerLetterString(0)).isEmpty();
        assertThat(randomLowerLetterString(17)).matches("[a-z]{17}");
    }

    @Test
    void randomNotNegativeDoubleTest() {
        assertThat(randomPositiveDouble(10.0)).isLessThan(10.0);
        assertThat(randomPositiveDouble(10.0)).isGreaterThan(0.0);
    }
}
