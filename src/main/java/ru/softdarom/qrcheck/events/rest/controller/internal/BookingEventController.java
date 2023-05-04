package ru.softdarom.qrcheck.events.rest.controller.internal;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.softdarom.qrcheck.events.config.swagger.annotations.ApiBookItems;
import ru.softdarom.qrcheck.events.config.swagger.annotations.ApiUnbookedItems;
import ru.softdarom.qrcheck.events.model.dto.external.request.CheckEventRequest;
import ru.softdarom.qrcheck.events.model.dto.external.response.BookingInfoResponse;
import ru.softdarom.qrcheck.events.service.internal.BookService;

@Tag(name = "Internal Booking Events", description = "Внутренний контроллер взаимодействия бронирования")
@RestController(value = "bookingEventController")
@RequestMapping("/internal/booking/events/{eventId}")
@PreAuthorize("hasRole(T(ru.softdarom.qrcheck.events.model.base.RoleType).API_KEY)")
public class BookingEventController {

    private final BookService bookService;

    @Autowired
    BookingEventController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiBookItems
    @PostMapping
    public BookingInfoResponse bookedTicketsAndOptionForCreateOrder(
            @PathVariable Long eventId,
            @RequestBody CheckEventRequest request
    ) {
        return bookService.bookItems(eventId, request);
    }

    @ApiUnbookedItems
    @DeleteMapping
    public BookingInfoResponse unbookedTicketsAndOptionForCreateOrder(
            @PathVariable Long eventId,
            @RequestBody CheckEventRequest request
    ) {
        return bookService.unbookedItems(eventId, request);
    }
}
