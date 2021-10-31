package ru.softdarom.qrcheck.events.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;

import javax.validation.constraints.NotEmpty;

@Data
@Generated
public class AddressDto {

    @JsonProperty("addressId")
    private Long addressId;

    @NotEmpty
    @JsonProperty("placeName")
    private String placeName;

    @NotEmpty
    @JsonProperty("address")
    private String address;

}