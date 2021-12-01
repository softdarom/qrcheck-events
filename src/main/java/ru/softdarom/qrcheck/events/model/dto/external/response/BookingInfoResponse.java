package ru.softdarom.qrcheck.events.model.dto.external.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import ru.softdarom.qrcheck.events.model.dto.external.BookingItemExternalDto;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingInfoResponse {

    @NotEmpty
    @JsonProperty("ticketsInfo")
    private Set<BookingItemExternalDto> ticketsInfo;

    @JsonProperty("optionsInfo")
    private Set<BookingItemExternalDto> optionsInfo;
}
