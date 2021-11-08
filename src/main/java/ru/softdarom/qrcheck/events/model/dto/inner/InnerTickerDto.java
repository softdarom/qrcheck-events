package ru.softdarom.qrcheck.events.model.dto.inner;

import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.base.TicketType;
import ru.softdarom.qrcheck.events.util.JsonHelper;

@Data
@Generated
public class InnerTickerDto {

    private Long id;

    private Integer quantity;

    private Integer availableQuantity;

    private Double cost;

    private Double price;

    private TicketType type;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }

}