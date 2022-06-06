package ru.softdarom.qrcheck.events.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocaleTypeDto {

    @NotNull
    @JsonProperty("code")
    private Integer code;

    @JsonProperty("name")
    private String name;

}
