package ru.softdarom.qrcheck.events.model.dto.inner;

import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.util.JsonHelper;

@Data
@Generated
public class InnerAddressDto {

    private Long id;

    private String placeName;

    private String address;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}