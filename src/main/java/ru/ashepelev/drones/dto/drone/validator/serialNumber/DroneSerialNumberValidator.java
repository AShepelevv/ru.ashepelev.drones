package ru.ashepelev.drones.dto.drone.validator.serialNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DroneSerialNumberValidator implements ConstraintValidator<DroneSerialNumber, String> {
    @Override
    public void initialize(DroneSerialNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       return value != null && !value.isEmpty() && value.length() <= 100;
    }
}
