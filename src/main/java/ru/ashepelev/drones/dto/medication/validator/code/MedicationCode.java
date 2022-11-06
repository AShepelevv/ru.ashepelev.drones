package ru.ashepelev.drones.dto.medication.validator.code;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { MedicationCodeValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface MedicationCode {
    String message() default "Medication code must be not null, not empty and match to [A-Z0-9_]+";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
