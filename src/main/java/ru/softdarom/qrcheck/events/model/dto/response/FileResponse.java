package ru.softdarom.qrcheck.events.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import ru.softdarom.qrcheck.events.model.dto.FileDto;
import ru.softdarom.qrcheck.events.util.JsonHelper;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@NoArgsConstructor
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileResponse {

    @NotEmpty
    @JsonProperty("files")
    private Collection<FileDto> images;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}