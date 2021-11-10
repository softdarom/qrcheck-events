package ru.softdarom.qrcheck.events.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import ru.softdarom.qrcheck.events.model.dto.TicketDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketResponse {

    @NotEmpty
    @JsonProperty("tickets")
    private Collection<TicketDto> tickets;

    @NotNull
    @JsonProperty("options")
    private boolean options;
}
