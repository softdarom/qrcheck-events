package ru.softdarom.qrcheck.events.service.external.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.dao.access.OptionAccessService;
import ru.softdarom.qrcheck.events.dao.access.TicketAccessService;
import ru.softdarom.qrcheck.events.model.dto.external.OptionExternalDto;
import ru.softdarom.qrcheck.events.model.dto.external.TicketExternalDto;
import ru.softdarom.qrcheck.events.model.dto.external.request.CheckEventRequest;
import ru.softdarom.qrcheck.events.model.dto.external.response.EventInfoResponse;
import ru.softdarom.qrcheck.events.model.dto.inner.Bookable;
import ru.softdarom.qrcheck.events.service.external.AbstractBookService;

import java.util.function.BiPredicate;

@Service
@Slf4j(topic = "SERVICE")
public class BookServiceImpl extends AbstractBookService {

    private final EventAccessService eventAccessService;
    private final OptionAccessService optionAccessService;
    private final TicketAccessService ticketAccessService;

    @Autowired
    BookServiceImpl(EventAccessService eventAccessService, OptionAccessService optionAccessService, TicketAccessService ticketAccessService) {
        this.eventAccessService = eventAccessService;
        this.optionAccessService = optionAccessService;
        this.ticketAccessService = ticketAccessService;
    }

    @Override
    public EventInfoResponse getEventInfoAndBook(Long eventId, CheckEventRequest request) {
        LOGGER.info("Booking for tickets and options");
        LOGGER.debug("Tickets: {}", request.getTickets());
        LOGGER.debug("Options: {}", request.getOptions());
        return buildResponse(eventId, request, optionAccessService::bookOption, ticketAccessService::bookTicket);
    }

    @Override
    public EventInfoResponse getEventInfoAndUnbooked(Long eventId, CheckEventRequest request) {
        LOGGER.info("Cancel a book for tickets and options");
        LOGGER.debug("Tickets: {}", request.getTickets());
        LOGGER.debug("Options: {}", request.getOptions());
        return buildResponse(eventId, request, optionAccessService::unbookedOption, ticketAccessService::unbookedTicket);
    }

    private EventInfoResponse buildResponse(Long eventId, CheckEventRequest request, BiPredicate<Long, Integer> optionPredicate, BiPredicate<Long, Integer> ticketPredicate) {
        var event = eventAccessService.findById(eventId);
        var response = new EventInfoResponse();
        response.setEventName(event.getName());
        response.setEventStart(event.getStartDateTime());
        response.setOptionsInfo(changeBookedStatuses(event.getOptions(), getId2DtoMap(request.getOptions()), optionPredicate, this::buildExternalOption));
        response.setTicketsInfo(changeBookedStatuses(event.getTickets(), getId2DtoMap(request.getTickets()), ticketPredicate, this::buildExternalTicket));
        return response;
    }

    private <T extends Bookable> OptionExternalDto buildExternalOption(T option) {
        var optionResp = new OptionExternalDto();
        optionResp.setId(option.getId());
        optionResp.setName(option.getName());
        optionResp.setPrice(option.getPrice());
        return optionResp;
    }

    private <T extends Bookable> TicketExternalDto buildExternalTicket(T ticket) {
        var ticketResp = new TicketExternalDto();
        ticketResp.setId(ticket.getId());
        ticketResp.setPrice(ticket.getPrice());
        ticketResp.setType("SOME_TYPE");
        return ticketResp;
    }
}
