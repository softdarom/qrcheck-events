package ru.softdarom.qrcheck.events.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class OptionDto {

    @JsonProperty("optionId")
    private Long optionId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("cost")
    private Double cost;
}