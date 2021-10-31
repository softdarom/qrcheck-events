package ru.softdarom.qrcheck.events.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class ImageDto {

    @JsonProperty("imageId")
    private Long imageId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("format")
    private String format;

}