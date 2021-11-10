package ru.softdarom.qrcheck.events.service;

import ru.softdarom.qrcheck.events.model.dto.response.TicketResponse;

public interface TicketService {

    TicketResponse getAvailableTickets(Long eventId);
}
