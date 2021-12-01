package ru.softdarom.qrcheck.events.model.dto.external.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.dto.external.BookedExternalDto;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Set;

@Data
@Generated
public class CheckEventRequest {

    @NotEmpty
    @JsonProperty("ticketsIds")
    private Collection<BookedExternalDto> tickets;

    @JsonProperty("optionsIds")
    private Collection<BookedExternalDto> options = Set.of();
}
