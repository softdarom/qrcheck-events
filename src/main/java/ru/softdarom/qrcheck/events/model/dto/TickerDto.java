package ru.softdarom.qrcheck.events.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;

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

}