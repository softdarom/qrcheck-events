package ru.softdarom.qrcheck.events.service.internal.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.dao.access.OptionAccessService;
import ru.softdarom.qrcheck.events.dao.access.TicketAccessService;
import ru.softdarom.qrcheck.events.model.dto.external.BookingItemExternalDto;
import ru.softdarom.qrcheck.events.model.dto.external.request.CheckEventRequest;
import ru.softdarom.qrcheck.events.model.dto.external.response.BookingInfoResponse;
import ru.softdarom.qrcheck.events.model.dto.internal.Bookable;
import ru.softdarom.qrcheck.events.service.internal.AbstractBookService;

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
    public BookingInfoResponse bookItems(Long eventId, CheckEventRequest request) {
        LOGGER.info("Booking for tickets and options");
        LOGGER.debug("Tickets: {}", request.getTickets());
        LOGGER.debug("Options: {}", request.getOptions());
        return buildResponse(eventId, request, optionAccessService::bookOption, ticketAccessService::bookTicket);
    }

    @Override
    public BookingInfoResponse unbookedItems(Long eventId, CheckEventRequest request) {
        LOGGER.info("Cancel a book for tickets and options");
        LOGGER.debug("Tickets: {}", request.getTickets());
        LOGGER.debug("Options: {}", request.getOptions());
        return buildResponse(eventId, request, optionAccessService::unbookedOption, ticketAccessService::unbookedTicket);
    }

    private BookingInfoResponse buildResponse(Long eventId, CheckEventRequest request,
                                              BiPredicate<Long, Integer> optionPredicate, BiPredicate<Long, Integer> ticketPredicate) {
        var event = eventAccessService.findById(eventId);
        var response = new BookingInfoResponse();
        response.setOptionsInfo(changeBookedStatuses(event.getOptions(), getId2DtoMap(request.getOptions()), optionPredicate, this::buildExternalItem));
        response.setTicketsInfo(changeBookedStatuses(event.getTickets(), getId2DtoMap(request.getTickets()), ticketPredicate, this::buildExternalItem));
        return response;
    }

    private <T extends Bookable> BookingItemExternalDto buildExternalItem(T ticket) {
        var dto = new BookingItemExternalDto();
        dto.setId(ticket.getId());
        dto.setPrice(ticket.getPrice());
        return dto;
    }
}
