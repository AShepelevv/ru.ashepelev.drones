package ru.ashepelev.drones.dto.validator.doubleRange;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

class DoubleRangeValidatorTest {
    private final DoubleRangeValidator validator = new DoubleRangeValidator();

    private static class Default {
        @DoubleRange
        public Double field;
    }

    private static class Min {
        @DoubleRange(min = 1.01)
        public Double field;
    }

    private static class Max {
        @DoubleRange(max = 3.14)
        public Double field;
    }

    private static class Custom {
        @DoubleRange(min = 1.01, max = 3.14)
        public Double field;
    }

    @Test
    void isValidDefault() throws NoSuchFieldException {
        DoubleRange annotation = Default.class.getField("field").getAnnotation(DoubleRange.class);
        validator.initialize(annotation);
        var context = mock(ConstraintValidatorContext.class);
        assertThat(validator.isValid(null, context)).isTrue();
        assertThat(validator.isValid(0.0, context)).isTrue();
        assertThat(validator.isValid(1.0, context)).isTrue();
        assertThat(validator.isValid(2.0, context)).isTrue();
        assertThat(validator.isValid(3.0, context)).isTrue();
        assertThat(validator.isValid(4.0, context)).isTrue();
        assertThat(validator.isValid(-Double.MAX_VALUE, context)).isTrue();
        assertThat(validator.isValid(Double.MAX_VALUE, context)).isTrue();
        verifyNoInteractions(context);
    }

    @Test
    void isValidMin() throws NoSuchFieldException {
        DoubleRange annotation = Min.class.getField("field").getAnnotation(DoubleRange.class);
        validator.initialize(annotation);
        var context = mock(ConstraintValidatorContext.class);
        assertThat(validator.isValid(null, context)).isTrue();
        assertThat(validator.isValid(0.0, context)).isFalse();
        assertThat(validator.isValid(1.0, context)).isFalse();
        assertThat(validator.isValid(2.0, context)).isTrue();
        assertThat(validator.isValid(3.0, context)).isTrue();
        assertThat(validator.isValid(4.0, context)).isTrue();
        assertThat(validator.isValid(-Double.MAX_VALUE, context)).isFalse();
        assertThat(validator.isValid(Double.MAX_VALUE, context)).isTrue();
        verifyNoInteractions(context);
    }

    @Test
    void isValidMax() throws NoSuchFieldException {
        DoubleRange annotation = Max.class.getField("field").getAnnotation(DoubleRange.class);
        validator.initialize(annotation);
        var context = mock(ConstraintValidatorContext.class);
        assertThat(validator.isValid(null, context)).isTrue();
        assertThat(validator.isValid(0.0, context)).isTrue();
        assertThat(validator.isValid(1.0, context)).isTrue();
        assertThat(validator.isValid(2.0, context)).isTrue();
        assertThat(validator.isValid(3.0, context)).isTrue();
        assertThat(validator.isValid(4.0, context)).isFalse();
        assertThat(validator.isValid(-Double.MAX_VALUE, context)).isTrue();
        assertThat(validator.isValid(Double.MAX_VALUE, context)).isFalse();
        verifyNoInteractions(context);
    }

    @Test
    void isValidCustom() throws NoSuchFieldException {
        DoubleRange annotation = Custom.class.getField("field").getAnnotation(DoubleRange.class);
        validator.initialize(annotation);
        var context = mock(ConstraintValidatorContext.class);
        assertThat(validator.isValid(null, context)).isTrue();
        assertThat(validator.isValid(0.0, context)).isFalse();
        assertThat(validator.isValid(1.0, context)).isFalse();
        assertThat(validator.isValid(2.0, context)).isTrue();
        assertThat(validator.isValid(3.0, context)).isTrue();
        assertThat(validator.isValid(4.0, context)).isFalse();
        assertThat(validator.isValid(-Double.MAX_VALUE, context)).isFalse();
        assertThat(validator.isValid(Double.MAX_VALUE, context)).isFalse();
        verifyNoInteractions(context);
    }
}
