package ru.softdarom.qrcheck.events.service;

import ru.softdarom.qrcheck.events.model.dto.TicketDto;

import java.util.Set;

public interface TicketService {

    Set<TicketDto> getAvailableTickets(Long eventId);
}
