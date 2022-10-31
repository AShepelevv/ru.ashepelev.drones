package ru.ashepelev.drones.dto.medication.validator.code;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.regex.Pattern.compile;

public class MedicationCodeValidator implements ConstraintValidator<MedicationCode, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return compile("[A-Z0-9_]+").matcher(value).matches();
    }
}
