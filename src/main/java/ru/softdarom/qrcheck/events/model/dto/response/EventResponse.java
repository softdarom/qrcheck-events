package ru.softdarom.qrcheck.events.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import ru.softdarom.qrcheck.events.model.base.EventType;
import ru.softdarom.qrcheck.events.model.base.GenreType;
import ru.softdarom.qrcheck.events.model.dto.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@NoArgsConstructor
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventResponse {

    @NotNull
    @JsonProperty("eventId")
    private Long id;

    @NotEmpty
    @JsonProperty("name")
    private String name;

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
    @JsonProperty("actual")
    private Boolean actual;

    @NotEmpty
    @JsonProperty("description")
    private String description;

    @NotNull
    @JsonProperty("address")
    private AddressDto address;

    @NotNull
    @JsonProperty("period")
    private PeriodDto period;

    @NotNull
    @JsonProperty("cover")
    private ImageDto cover;

    @NotEmpty
    @JsonProperty("images")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Collection<ImageDto> images;

    @NotEmpty
    @JsonProperty("tickets")
    private Collection<TicketDto> tickets;

    @NotEmpty
    @JsonProperty("options")
    private Collection<OptionDto> options;

    public EventResponse(Long id) {
        this.id = id;
    }
}