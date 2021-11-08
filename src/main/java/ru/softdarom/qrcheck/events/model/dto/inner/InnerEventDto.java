package ru.softdarom.qrcheck.events.model.dto.inner;

import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.base.EventType;
import ru.softdarom.qrcheck.events.model.base.GenreType;
import ru.softdarom.qrcheck.events.util.JsonHelper;

import java.util.Collection;

@Data
@Generated
public class InnerEventDto {

    private Long id;

    private Long externalUserId;

    private String name;

    private String description;

    private EventType event;

    private String ageRestrictions;

    private Boolean draft;

    private Collection<GenreType> genres;

    private InnerAddressDto address;

    private InnerPeriodDto period;

    private Collection<InnerTickerDto> tickets;

    private Collection<InnerOptionDto> options;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}