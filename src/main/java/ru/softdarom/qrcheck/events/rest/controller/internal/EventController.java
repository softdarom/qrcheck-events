package ru.softdarom.qrcheck.events.rest.controller.internal;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.softdarom.qrcheck.events.config.swagger.annotations.ApiGetEventOwnerIdById;
import ru.softdarom.qrcheck.events.config.swagger.annotations.ApiGetEventsById;
import ru.softdarom.qrcheck.events.config.swagger.annotations.ApiGetEventsByIds;
import ru.softdarom.qrcheck.events.model.dto.external.response.EventInfoResponse;
import ru.softdarom.qrcheck.events.service.internal.ExternalEventService;

import java.util.Collection;
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
    public Set<EventInfoResponse> getEventsInfo(@RequestParam Collection<Long> eventsId) {
        return eventService.getEventsInfo(eventsId);
    }

    @ApiGetEventsById
    @GetMapping("/{eventId}")
    public EventInfoResponse getEventInfo(@PathVariable("eventId") Long eventId) {
        return eventService.getEventInfo(eventId);
    }

    @ApiGetEventOwnerIdById
    @GetMapping("/owner/{eventId}")
    public Long getEventOwnerId(@PathVariable("eventId") Long eventId) {
        return eventService.getEventOwnerId(eventId);
    }
}
