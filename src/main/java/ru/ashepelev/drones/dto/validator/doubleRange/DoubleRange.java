package ru.ashepelev.drones.dto.validator.doubleRange;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.Double.MAX_VALUE;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { DoubleRangeValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface DoubleRange {
    String message() default "Double value is not in allowed range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    double min() default -MAX_VALUE;

    double max() default MAX_VALUE;
}
