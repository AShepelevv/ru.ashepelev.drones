package ru.ashepelev.drones.dto.validator.doubeRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DoubleRangeValidator implements ConstraintValidator<DoubleRange, Double> {
    private double min;
    private double max;

    @Override
    public void initialize(DoubleRange constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        double EPS = 1 ^ -9;
        return value != null && value + EPS >= min && value - EPS <= max;
    }
}
