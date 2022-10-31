package ru.ashepelev.drones.dto.drone.validator.serialNumber;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { DroneSerialNumberValidator.class })
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface DroneSerialNumber {
    String message() default "Drone serial number must be not null, not empty and have a length of no more than 100 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
