package ru.softdarom.qrcheck.events.dao.access.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.dao.access.TicketAccessService;
import ru.softdarom.qrcheck.events.dao.entity.TicketEntity;
import ru.softdarom.qrcheck.events.dao.repository.TicketRepository;
import ru.softdarom.qrcheck.events.exception.NotFoundException;
import ru.softdarom.qrcheck.events.mapper.impl.InnerTicketDtoMapper;
import ru.softdarom.qrcheck.events.model.base.ActiveType;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerTicketDto;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "SERVICE")
public class TicketAccessServiceImpl implements TicketAccessService {

    private final TicketRepository ticketRepository;
    private final InnerTicketDtoMapper ticketMapper;

    @Autowired
    TicketAccessServiceImpl(TicketRepository ticketRepository, InnerTicketDtoMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    @Transactional
    public Set<InnerTicketDto> findByEventIdAndActiveEvent(Long eventId) {
        Assert.notNull(eventId, "The 'eventId' must not be null!");
        return ticketRepository.findAllByEventIdAndEvent_Active(eventId, ActiveType.ENABLED).stream()
                .map(ticketMapper::convertToDestination)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Boolean bookTicket(Long ticketId, Integer quantity) {
        Assert.notNull(ticketId, "The 'ticketId' must not be null!");
        Assert.notNull(quantity, "The 'quantity' must not be null!");
        var ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Not found ticket with id: " + ticketId));
        if (canBook(ticket, quantity)) {
            LOGGER.info("A ticket (id: {}, count: {}) will be booked.", ticketId, quantity);
            ticket.setAvailableQuantity(ticket.getAvailableQuantity() - quantity);
            ticketRepository.save(ticket);
            return true;
        } else {
            LOGGER.warn(
                    "An ticket (id: {}, quantity: {}) can not be booked! Available quantity of tickets are '{}'",
                    ticketId, quantity, ticket.getAvailableQuantity()
            );
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean unbookedTicket(Long ticketId, Integer quantity) {
        Assert.notNull(ticketId, "The 'ticketId' must not be null!");
        Assert.notNull(quantity, "The 'quantity' must not be null!");
        LOGGER.info("A ticket (id: {}, count: {}) will be unbooked.", ticketId, quantity);
        var ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Not found ticket with id: " + ticketId));
        ticket.setAvailableQuantity(ticket.getAvailableQuantity() + quantity);
        ticketRepository.save(ticket);
        return true;
    }

    private boolean canBook(TicketEntity ticket, Integer bookingQuantity) {
        return ticket.getAvailableQuantity() - bookingQuantity >= 0;
    }
}
