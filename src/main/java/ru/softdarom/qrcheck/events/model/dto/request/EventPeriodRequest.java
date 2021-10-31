package ru.softdarom.qrcheck.events.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.dto.PeriodDto;

import javax.validation.constraints.NotNull;

@Data
@Generated
public class EventPeriodRequest {

    @NotNull
    @JsonProperty("eventId")
    private Long eventId;

    @NotNull
    @JsonProperty("period")
    private PeriodDto period;
}