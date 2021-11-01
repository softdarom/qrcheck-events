package ru.softdarom.qrcheck.events.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.base.TicketType;

@Data
@Generated
public class TickerDto {

    @JsonProperty("ticketId")
    private Long ticketId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("cost")
    private Double cost;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("type")
    private TicketType type;

}