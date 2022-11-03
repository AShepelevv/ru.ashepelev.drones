package ru.ashepelev.drones.dto.drone.validator.serialNumber;

import io.zonky.test.db.util.RandomStringUtils;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

class DroneSerialNumberValidatorTest {
    private final DroneSerialNumberValidator validator = new DroneSerialNumberValidator();

    @Test
    void isValid() {
        var context = mock(ConstraintValidatorContext.class);
        assertThat(validator.isValid(null, context)).isTrue();
        assertThat(validator.isValid("", context)).isFalse();
        assertThat(validator.isValid(RandomStringUtils.randomAlphabetic(100), context)).isTrue();
        assertThat(validator.isValid(RandomStringUtils.randomAlphabetic(101), context)).isFalse();
        assertThat(validator.isValid(" !@#$%^&*()_+\n\tafeegr092", context)).isTrue();
        verifyNoInteractions(context);
    }
}
