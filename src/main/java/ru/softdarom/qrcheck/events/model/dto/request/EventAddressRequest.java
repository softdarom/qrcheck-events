package ru.softdarom.qrcheck.events.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.dto.AddressDto;

import javax.validation.constraints.NotNull;

@Data
@Generated
public class EventAddressRequest {

    @NotNull
    @JsonProperty("eventId")
    private Long eventId;

    @NotNull
    @JsonProperty("address")
    private AddressDto address;
}