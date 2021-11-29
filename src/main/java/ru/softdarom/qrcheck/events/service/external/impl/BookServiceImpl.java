package ru.softdarom.qrcheck.events.service.external.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.model.dto.external.BookedExternalDto;
import ru.softdarom.qrcheck.events.model.dto.external.request.CheckEventRequest;
import ru.softdarom.qrcheck.events.model.dto.external.response.EventInfoResponse;
import ru.softdarom.qrcheck.events.service.external.BookOptionService;
import ru.softdarom.qrcheck.events.service.external.BookService;
import ru.softdarom.qrcheck.events.service.external.BookTicketService;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "SERVICE")
public class BookServiceImpl implements BookService {

    private final EventAccessService eventAccessService;
    private final BookOptionService bookOptionService;
    private final BookTicketService bookTicketService;

    @Autowired
    BookServiceImpl(EventAccessService eventAccessService, BookOptionService bookOptionService,
                    BookTicketService bookTicketService) {
        this.eventAccessService = eventAccessService;
        this.bookOptionService = bookOptionService;
        this.bookTicketService = bookTicketService;
    }

    @Override
    public EventInfoResponse getEventInfoAndBook(Long eventId, CheckEventRequest request) {
        var event = eventAccessService.findById(eventId);
        var response = new EventInfoResponse();
        response.setEventName(event.getName());
        response.setEventStart(event.getStartDateTime());
        response.setOptionsInfo(bookOptionService.bookOptions(event.getOptions(), getId2DtoMap(request.getTickets())));
        response.setTicketsInfo(bookTicketService.bookTickets(event.getTickets(), getId2DtoMap(request.getTickets())));
        return response;
    }

    @Override
    public EventInfoResponse getEventInfoAndUnbooked(Long eventId, CheckEventRequest request) {
        var event = eventAccessService.findById(eventId);
        var response = new EventInfoResponse();
        response.setEventName(event.getName());
        response.setEventStart(event.getStartDateTime());
        response.setOptionsInfo(bookOptionService.unbookedOptions(event.getOptions(), getId2DtoMap(request.getTickets())));
        response.setTicketsInfo(bookTicketService.unbookedTickets(event.getTickets(), getId2DtoMap(request.getTickets())));
        return response;
    }

    private Map<Long, BookedExternalDto> getId2DtoMap(Collection<BookedExternalDto> itemRequest) {
        return itemRequest.stream()
                .collect(Collectors.toMap(BookedExternalDto::getId, Function.identity()));
    }
}
