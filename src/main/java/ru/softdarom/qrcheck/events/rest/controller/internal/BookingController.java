package ru.softdarom.qrcheck.events.rest.controller.internal;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.softdarom.qrcheck.events.config.swagger.annotations.ApiBookItems;
import ru.softdarom.qrcheck.events.config.swagger.annotations.ApiUnbookedItems;
import ru.softdarom.qrcheck.events.model.dto.external.request.CheckEventRequest;
import ru.softdarom.qrcheck.events.model.dto.external.response.EventInfoResponse;
import ru.softdarom.qrcheck.events.service.external.BookService;

@Tag(name = "Internal Events", description = "Внутренний контроллер взаимодействия с events")
@RestController(value = "internalEventController")
@RequestMapping("/internal/events")
public class BookingController {

    private final BookService bookService;

    @Autowired
    BookingController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiBookItems
    @PostMapping("/{eventId}")
    public ResponseEntity<EventInfoResponse> bookedTicketsAndOptionForCreateOrder(
            @PathVariable Long eventId,
            @RequestBody CheckEventRequest request
    ) {
        return ResponseEntity.ok(bookService.getEventInfoAndBook(eventId, request));
    }

    @ApiUnbookedItems
    @DeleteMapping("/{eventId}")
    public ResponseEntity<EventInfoResponse> unbookedTicketsAndOptionForCreateOrder(
            @PathVariable Long eventId,
            @RequestBody CheckEventRequest request
    ) {
        return ResponseEntity.ok(bookService.getEventInfoAndUnbooked(eventId, request));
    }
}
