package ru.softdarom.qrcheck.events.config.validator.annotations;

import ru.softdarom.qrcheck.events.config.validator.DateConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateConstraintValidator.class)
public @interface DateConstraint {

    String message() default "Start date and time must be before than now!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
