package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.dao.access.OptionAccessService;
import ru.softdarom.qrcheck.events.dao.access.TicketAccessService;
import ru.softdarom.qrcheck.events.mapper.impl.TicketResponseMapper;
import ru.softdarom.qrcheck.events.model.dto.response.TicketResponse;
import ru.softdarom.qrcheck.events.service.mobile.TicketService;

import java.util.stream.Collectors;

@Service
@Slf4j(topic = "SERVICE")
public class TicketServiceImpl implements TicketService {

    private final TicketAccessService ticketAccessService;
    private final OptionAccessService optionAccessService;
    private final TicketResponseMapper ticketMapper;

    @Autowired
    TicketServiceImpl(TicketAccessService ticketAccessService, OptionAccessService optionAccessService,
                      TicketResponseMapper ticketMapper) {
        this.ticketAccessService = ticketAccessService;
        this.optionAccessService = optionAccessService;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public TicketResponse getAvailableTickets(Long eventId) {
        Assert.notNull(eventId, "The 'eventId' must not be null!");
        LOGGER.info("Getting a tickets by eventId: {}", eventId);
        var tickets = ticketAccessService.findByEventIdAndActiveEvent(eventId).stream()
                .map(ticketMapper::convertToDestination)
                .collect(Collectors.toSet());
        return new TicketResponse(tickets, optionAccessService.isEventHasOptions(eventId));
    }
}
