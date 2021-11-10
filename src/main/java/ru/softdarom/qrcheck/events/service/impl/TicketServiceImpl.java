package ru.softdarom.qrcheck.events.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.dao.access.TicketAccessService;
import ru.softdarom.qrcheck.events.mapper.impl.TicketResponseMapper;
import ru.softdarom.qrcheck.events.model.dto.TicketDto;
import ru.softdarom.qrcheck.events.service.TicketService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "TICKETS-SERVICE")
public class TicketServiceImpl implements TicketService {

    private final TicketAccessService ticketAccessService;
    private final TicketResponseMapper ticketMapper;

    @Autowired
    TicketServiceImpl(TicketAccessService ticketAccessService, TicketResponseMapper ticketMapper) {
        this.ticketAccessService = ticketAccessService;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public Set<TicketDto> getAvailableTickets(Long eventId) {
        Assert.notNull(eventId, "The 'eventId' must not be null!");
        LOGGER.info("Getting a tickets by eventId: {}", eventId);
        return ticketAccessService.findByEventIdAndActiveEvent(eventId).stream()
                .map(ticketMapper::convertToDestination)
                .collect(Collectors.toSet());
    }
}
