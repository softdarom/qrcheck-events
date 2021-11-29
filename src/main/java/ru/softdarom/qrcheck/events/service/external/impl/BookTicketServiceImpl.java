package ru.softdarom.qrcheck.events.service.external.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.TicketAccessService;
import ru.softdarom.qrcheck.events.exception.InvalidBookOperation;
import ru.softdarom.qrcheck.events.model.dto.external.BookedExternalDto;
import ru.softdarom.qrcheck.events.model.dto.external.TicketExternalDto;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerTicketDto;
import ru.softdarom.qrcheck.events.service.external.BookTicketService;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "SERVICE")
public class BookTicketServiceImpl implements BookTicketService {

    private final TicketAccessService ticketAccessService;

    @Autowired
    BookTicketServiceImpl(TicketAccessService ticketAccessService) {
        this.ticketAccessService = ticketAccessService;
    }

    @Override
    @Transactional
    public Set<TicketExternalDto> bookTickets(Collection<InnerTicketDto> eventTickets, Map<Long, BookedExternalDto> bookItems) {
        var neededTicket = eventTickets.stream()
                .filter(it -> bookItems.containsKey(it.getId()))
                .collect(Collectors.toSet());
        checkCountOfNeededAndExternalItems(neededTicket, bookItems.keySet());
        return neededTicket.stream()
                .map(ticket -> bookTicket(ticket, bookItems.get(ticket.getId())))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Set<TicketExternalDto> unbookedTickets(Collection<InnerTicketDto> eventTickets, Map<Long, BookedExternalDto> bookItems) {
        var neededTicket = eventTickets.stream()
                .filter(it -> bookItems.containsKey(it.getId()))
                .collect(Collectors.toSet());
        checkCountOfNeededAndExternalItems(neededTicket, bookItems.keySet());
        return neededTicket.stream()
                .map(ticket -> unbookedTicket(ticket, bookItems.get(ticket.getId())))
                .collect(Collectors.toSet());
    }

    private void checkCountOfNeededAndExternalItems(Collection<?> neededItems, Collection<?> externalItems) {
        if (neededItems.size() != externalItems.size()) {
            throw new InvalidBookOperation("The number of tickets does not match the number of tickets in the event!");
        }
    }

    private TicketExternalDto bookTicket(InnerTicketDto ticket, BookedExternalDto reqTicket) {
        if (!ticketAccessService.bookTicket(reqTicket.getId(), reqTicket.getQuantity())) {
            throw new InvalidBookOperation("There are not enough tickets with id: " + ticket.getId());
        } else {
            return getTicketInfo(ticket);
        }
    }

    private TicketExternalDto unbookedTicket(InnerTicketDto ticket, BookedExternalDto reqTicket) {
        if (!ticketAccessService.unbookedTicket(reqTicket.getId(), reqTicket.getQuantity())) {
            throw new InvalidBookOperation("There are not enough tickets with id: " + ticket.getId());
        } else {
            return getTicketInfo(ticket);
        }
    }

    private TicketExternalDto getTicketInfo(InnerTicketDto ticket) {
        var ticketResp = new TicketExternalDto();
        ticketResp.setId(ticket.getId());
        ticketResp.setPrice(ticket.getPrice());
        ticketResp.setType("SOME_TYPE");
        return ticketResp;
    }
}
