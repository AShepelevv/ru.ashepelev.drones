package ru.ashepelev.drones.dto.validator.doubleRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static ru.ashepelev.drones.utils.Precision.of;

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
        if (value == null) return true;
        return of(value).isGreaterOrEqualTo(min) && of(value).isLessOrEqualTo(max);
    }
}
