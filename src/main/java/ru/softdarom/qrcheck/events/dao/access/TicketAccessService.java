package ru.softdarom.qrcheck.events.dao.access;

import ru.softdarom.qrcheck.events.model.dto.inner.InnerTicketDto;

import java.util.Set;

public interface TicketAccessService {

    Set<InnerTicketDto> findByEventIdAndActiveEvent(Long eventId);
}
