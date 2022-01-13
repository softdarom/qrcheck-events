package ru.softdarom.qrcheck.events.rest.controller.internal;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.softdarom.qrcheck.events.config.swagger.annotations.ApiGetEventsById;
import ru.softdarom.qrcheck.events.config.swagger.annotations.ApiGetEventsByIds;
import ru.softdarom.qrcheck.events.model.dto.external.response.EventInfoResponse;
import ru.softdarom.qrcheck.events.service.external.ExternalEventService;

import java.util.Set;

@Tag(name = "Internal Events", description = "Внутренний контроллер взаимодействия с events")
@RestController(value = "internalEventController")
@RequestMapping("/internal/events")
public class EventController {

    private final ExternalEventService eventService;

    @Autowired
    EventController(ExternalEventService eventService) {
        this.eventService = eventService;
    }

    @ApiGetEventsByIds
    @GetMapping
    public ResponseEntity<Set<EventInfoResponse>> getEventsInfo(@RequestParam Set<Long> eventsId) {
        return ResponseEntity.ok(eventService.getEventsInfo(eventsId));
    }

    @ApiGetEventsById
    @GetMapping("/{eventId}")
    public ResponseEntity<EventInfoResponse> getEventInfo(@PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(eventService.getEventInfo(eventId));
    }
}
