package ru.softdarom.qrcheck.events.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.base.TicketType;

import javax.validation.constraints.NotNull;

@Data
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TickerDto {

    @JsonProperty("ticketId")
    private Long ticketId;

    @NotNull
    @JsonProperty("quantity")
    private Integer quantity;

    @NotNull
    @JsonProperty("cost")
    private Double cost;

    @JsonProperty("price")
    private Double price;

    @NotNull
    @JsonProperty("type")
    private TicketType type;

}