package ru.softdarom.qrcheck.events.dao.access.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.dao.access.TicketAccessService;
import ru.softdarom.qrcheck.events.dao.repository.TicketRepository;
import ru.softdarom.qrcheck.events.mapper.impl.InnerTicketDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerTicketDto;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "TICKETS-ACCESS-SERVICE")
public class TicketAccessServiceImpl implements TicketAccessService {

    private final TicketRepository ticketRepository;
    private final InnerTicketDtoMapper ticketMapper;

    @Autowired
    TicketAccessServiceImpl(TicketRepository ticketRepository, InnerTicketDtoMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public Set<InnerTicketDto> findByEventIdAndActiveEvent(Long eventId) {
        Assert.notNull(eventId, "The 'eventId' must not be null!");
        return ticketRepository.findAllByEventIdAndEvent_Active(eventId, true).stream()
                .map(ticketMapper::convertToDestination)
                .collect(Collectors.toSet());
    }
}
