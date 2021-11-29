package ru.softdarom.qrcheck.events.model.dto.external.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import ru.softdarom.qrcheck.events.model.dto.external.OptionExternalDto;
import ru.softdarom.qrcheck.events.model.dto.external.TicketExternalDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventInfoResponse {

    @NotNull
    @JsonProperty("eventName")
    @Schema(type = "string", description = "Наименование события")
    private String eventName;

    @NotNull
    @JsonProperty("eventStart")
    @Schema(description = "Время начала события")
    private LocalDateTime eventStart;

    @NotNull
    @JsonProperty("ticketsInfo")
    private Set<TicketExternalDto> ticketsInfo;

    @NotNull
    @JsonProperty("optionsInfo")
    private Set<OptionExternalDto> optionsInfo;
}
