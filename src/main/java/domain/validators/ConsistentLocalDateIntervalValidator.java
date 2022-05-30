package domain.validators;

import domain.interfaces.LocalDateInterval;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConsistentLocalDateIntervalValidator implements ConstraintValidator<ConsistentLocalDateInterval, LocalDateInterval> {

    @Override
    public boolean isValid(LocalDateInterval value, ConstraintValidatorContext context) {
        return value.getStartDate().compareTo(value.getEndDate()) <= 0;
    }
}
