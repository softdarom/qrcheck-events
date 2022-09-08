package ru.softdarom.qrcheck.events.config.validator;

import ru.softdarom.qrcheck.events.config.validator.annotations.PriceConstraint;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public class PriceConstraintValidator implements ConstraintValidator<PriceConstraint, EventRequest> {

    //10^10 = 10.000.000.000
    private static final BigDecimal MAX_TOTAL_COST = new BigDecimal("10000000000");

    @Override
    public boolean isValid(EventRequest request, ConstraintValidatorContext context) {
        var ticketTotalCost = request.getTickets().stream()
                .map(it -> it.getCost().multiply(BigDecimal.valueOf(it.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var optionTotalCost = Optional.ofNullable(request.getOptions()).orElse(Set.of()).stream()
                .map(it -> it.getCost().multiply(BigDecimal.valueOf(it.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var totalCost = ticketTotalCost.add(optionTotalCost);
        return totalCost.compareTo(MAX_TOTAL_COST) <= 0;
    }
}
