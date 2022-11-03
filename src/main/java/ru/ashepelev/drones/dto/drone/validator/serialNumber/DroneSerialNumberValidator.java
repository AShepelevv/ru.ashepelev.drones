package ru.ashepelev.drones.dto.drone.validator.serialNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DroneSerialNumberValidator implements ConstraintValidator<DroneSerialNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return !value.isEmpty() && value.length() <= 100;
    }
}
