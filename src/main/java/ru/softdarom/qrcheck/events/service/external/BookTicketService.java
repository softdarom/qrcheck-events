package ru.softdarom.qrcheck.events.service.external;

import ru.softdarom.qrcheck.events.model.dto.external.BookedExternalDto;
import ru.softdarom.qrcheck.events.model.dto.external.TicketExternalDto;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerTicketDto;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface BookTicketService {

    Set<TicketExternalDto> bookTickets(Collection<InnerTicketDto> eventTickets, Map<Long, BookedExternalDto> bookItems);

    Set<TicketExternalDto> unbookedTickets(Collection<InnerTicketDto> eventTickets, Map<Long, BookedExternalDto> bookItems);
}
