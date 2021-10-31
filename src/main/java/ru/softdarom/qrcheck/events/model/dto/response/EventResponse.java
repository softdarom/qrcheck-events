package ru.softdarom.qrcheck.events.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import ru.softdarom.qrcheck.events.model.base.EventType;
import ru.softdarom.qrcheck.events.model.base.GenreType;
import ru.softdarom.qrcheck.events.model.dto.*;

import java.util.Collection;

@Data
@NoArgsConstructor
@Generated
@EqualsAndHashCode(callSuper = false)
public class EventResponse extends BaseResponse {

    @JsonProperty("eventId")
    private Long eventId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private EventType event;

    @JsonProperty("ageRestrictions")
    private String ageRestrictions;

    @JsonProperty("genre")
    private GenreType genre;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("description")
    private String description;

    @JsonProperty("address")
    private AddressDto address;

    @JsonProperty("period")
    private PeriodDto period;

    @JsonProperty("cover")
    private ImageDto cover;

    @JsonProperty("images")
    private Collection<ImageDto> images;

    @JsonProperty("tickets")
    private Collection<TickerDto> tickets;

    @JsonProperty("options")
    private Collection<OptionDto> options;
}