package ru.softdarom.qrcheck.events.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PeriodDto {

    @JsonProperty("periodId")
    private Long periodId;

    @NotNull
    @JsonProperty("startDate")
    private LocalDate startDate;

    @NotNull
    @JsonProperty("startTime")
    private LocalTime startTime;
}