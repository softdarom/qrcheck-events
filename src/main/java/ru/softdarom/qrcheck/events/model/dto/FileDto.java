package ru.softdarom.qrcheck.events.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.softdarom.qrcheck.events.util.JsonHelper;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("url")
    private String url;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }

}