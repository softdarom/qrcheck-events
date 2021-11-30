package ru.softdarom.qrcheck.events.model.dto.external;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketExternalDto {

    @NotNull
    @JsonProperty("id")
    @Schema(
            type = "integer", format = "int64", minimum = "0",
            description = "Идентификатор билета который забронирован"
    )
    private Long id;

    @NotNull
    @JsonProperty("price")
    @Schema(
            type = "integer", format = "decimal", minimum = "0",
            description = "Цена билета"
    )
    private BigDecimal price;
}
