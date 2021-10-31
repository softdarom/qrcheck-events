package ru.softdarom.qrcheck.events.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.dto.TickerDto;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Generated
public class EventTicketRequest {

    @NotNull
    @JsonProperty("eventId")
    private Long eventId;

    @NotNull
    @JsonProperty("tickets")
    private Collection<TickerDto> tickets;
}