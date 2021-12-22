package ru.softdarom.qrcheck.events.dao.access;

import ru.softdarom.qrcheck.events.model.dto.internal.InternalTicketDto;

import java.util.Set;

public interface TicketAccessService {

    Set<InternalTicketDto> findByEventIdAndActiveEvent(Long eventId);

    Boolean bookTicket(Long optionId, Integer quantity);

    Boolean unbookedTicket(Long ticketId, Integer quantity);
}
