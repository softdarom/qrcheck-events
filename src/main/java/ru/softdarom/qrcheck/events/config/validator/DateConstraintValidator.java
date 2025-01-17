package ru.softdarom.qrcheck.events.config.validator;

import ru.softdarom.qrcheck.events.config.validator.annotations.DateConstraint;
import ru.softdarom.qrcheck.events.model.dto.PeriodDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class DateConstraintValidator implements ConstraintValidator<DateConstraint, PeriodDto> {

    @Override
    public boolean isValid(PeriodDto request, ConstraintValidatorContext constraintValidatorContext) {
        return LocalDateTime.of(request.getStartDate(), request.getStartTime()).isAfter(LocalDateTime.now());
    }

}
