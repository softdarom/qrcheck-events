package ru.softdarom.qrcheck.events.model.dto.inner;

import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.util.JsonHelper;

@Data
@Generated
public class EventDto {

    private Long id;

    private Long externalUserId;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}