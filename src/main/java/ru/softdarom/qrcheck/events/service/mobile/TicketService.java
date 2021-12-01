package ru.softdarom.qrcheck.events.service.mobile;

import ru.softdarom.qrcheck.events.model.dto.response.TicketResponse;

public interface TicketService {

    TicketResponse getAvailableTickets(Long eventId);
}
