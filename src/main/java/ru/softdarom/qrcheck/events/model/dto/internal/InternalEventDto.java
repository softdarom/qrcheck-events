package ru.softdarom.qrcheck.events.model.dto.internal;

import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.base.EventType;
import ru.softdarom.qrcheck.events.model.base.GenreType;
import ru.softdarom.qrcheck.events.util.JsonHelper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Generated
public class InternalEventDto {

    private Long id;

    private Long externalUserId;

    private String name;

    private String ageRestrictions;

    private String description;

    private LocalDateTime startDateTime;

    private BigDecimal totalAmount;

    private BigDecimal currentAmount;

    private LocalDateTime overDate;

    private Boolean draft;

    private Collection<GenreType> genres;

    private InternalAddressDto address;

    private EventType type;

    private Collection<InternalImageDto> images;

    private Collection<InternalOptionDto> options;

    private Collection<InternalTicketDto> tickets;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}