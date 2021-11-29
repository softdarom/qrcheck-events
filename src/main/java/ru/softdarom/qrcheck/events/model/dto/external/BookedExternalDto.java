package ru.softdarom.qrcheck.events.model.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Generated;

import javax.validation.constraints.NotNull;

@Data
@Generated
public class BookedExternalDto {

    @NotNull
    @JsonProperty("id")
    @Schema(
            type = "integer", format = "int64", minimum = "0",
            description = "Идентификатор сущности которая будет забронирована"
    )
    private Long id;

    @NotNull
    @JsonProperty("quantity")
    @Schema(
            type = "integer", format = "int32", minimum = "0",
            description = "Количество которое будет забронировано"
    )
    private Integer quantity;
}
