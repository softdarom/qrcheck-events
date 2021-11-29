package ru.softdarom.qrcheck.events.model.dto.inner;

import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.util.JsonHelper;

import java.math.BigDecimal;

@Data
@Generated
public class InnerTicketDto {

    private Long id;

    private Integer quantity;

    private Integer availableQuantity;

    private BigDecimal cost;

    private BigDecimal price;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }

}