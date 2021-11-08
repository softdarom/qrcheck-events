package ru.softdarom.qrcheck.events.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;

import javax.validation.constraints.NotEmpty;

@Data
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {

    @JsonProperty("addressId")
    private Long id;

    @NotEmpty
    @JsonProperty("placeName")
    private String placeName;

    @NotEmpty
    @JsonProperty("address")
    private String address;

}