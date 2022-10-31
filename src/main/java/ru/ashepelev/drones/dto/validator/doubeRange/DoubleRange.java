package ru.ashepelev.drones.dto.validator.doubeRange;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { DoubleRangeValidator.class })
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface DoubleRange {
    String message() default "Double value is not in allowed range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    double min() default Double.MIN_VALUE;

    double max() default Double.MAX_VALUE;
}
