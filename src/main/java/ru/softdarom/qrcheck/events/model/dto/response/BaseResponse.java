package ru.softdarom.qrcheck.events.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {

    private Long errorId;
    private String message;

    public BaseResponse(String message) {
        this.message = message;
    }
}