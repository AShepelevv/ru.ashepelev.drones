package ru.ashepelev.drones.dto.medication.validator.code;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

class MedicationCodeValidatorTest {
    private final MedicationCodeValidator validator = new MedicationCodeValidator();

    @Test
    void isValid() {
        var context = mock(ConstraintValidatorContext.class);
        assertThat(validator.isValid("ABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789", context)).isTrue();
        assertThat(validator.isValid(null, context)).isTrue();
        assertThat(validator.isValid("", context)).isFalse();
        assertThat(validator.isValid("a", context)).isFalse();
        verifyNoInteractions(context);
    }
}
