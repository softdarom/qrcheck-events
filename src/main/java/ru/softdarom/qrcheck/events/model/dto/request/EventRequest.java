package ru.softdarom.qrcheck.events.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.base.EventType;
import ru.softdarom.qrcheck.events.model.base.GenreType;
import ru.softdarom.qrcheck.events.model.dto.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Generated
public class EventRequest {

    @NotNull
    @JsonProperty("eventId")
    private Long id;

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @NotEmpty
    @JsonProperty("description")
    private String description;

    @NotNull
    @JsonProperty("type")
    private EventType event;

    @NotEmpty
    @JsonProperty("ageRestrictions")
    private String ageRestrictions;

    @NotEmpty
    @JsonProperty("genres")
    private Collection<GenreType> genres;

    @NotNull
    @JsonProperty("address")
    private AddressDto address;

    @NotNull
    @JsonProperty("period")
    private PeriodDto period;

    @NotEmpty
    @JsonProperty("tickets")
    private Collection<TickerDto> tickets;

    @NotEmpty
    @JsonProperty("options")
    private Collection<OptionDto> options;

}