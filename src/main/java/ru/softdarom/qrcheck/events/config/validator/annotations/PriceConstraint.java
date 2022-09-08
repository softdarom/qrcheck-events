package ru.softdarom.qrcheck.events.config.validator.annotations;

import ru.softdarom.qrcheck.events.config.validator.PriceConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PriceConstraintValidator.class)
public @interface PriceConstraint {

    String message() default "A total price of tickets/options can not be more than 10^10!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
