package ru.softdarom.qrcheck.events.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.dto.PeriodDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Generated
public class EventDescriptionRequest {

    @NotNull
    @JsonProperty("eventId")
    private Long eventId;

    @NotEmpty
    @JsonProperty("description")
    private String description;
}