package ru.softdarom.qrcheck.events.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @JsonProperty("errorId")
    private Long errorId;
    @JsonProperty("message")
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}