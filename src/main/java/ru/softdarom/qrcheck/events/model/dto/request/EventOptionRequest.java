package ru.softdarom.qrcheck.events.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.dto.OptionDto;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Generated
public class EventOptionRequest {

    @NotNull
    @JsonProperty("eventId")
    private Long eventId;

    @NotNull
    @JsonProperty("options")
    private Collection<OptionDto> options;
}