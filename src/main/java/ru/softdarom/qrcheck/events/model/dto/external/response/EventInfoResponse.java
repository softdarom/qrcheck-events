package ru.softdarom.qrcheck.events.model.dto.external.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.softdarom.qrcheck.events.model.dto.external.OptionExternalDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class EventInfoResponse {

    @NotNull
    @JsonProperty("id")
    private Long id;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("startDateTime")
    private LocalDateTime startDateTime;

    @JsonProperty("options")
    private Set<OptionExternalDto> options;
}
