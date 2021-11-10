package ru.softdarom.qrcheck.events.rest.controller.mobile;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.softdarom.qrcheck.events.config.swagger.annotations.*;
import ru.softdarom.qrcheck.events.model.base.EventType;
import ru.softdarom.qrcheck.events.model.base.ImageType;
import ru.softdarom.qrcheck.events.model.dto.TicketDto;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;
import ru.softdarom.qrcheck.events.service.EventService;
import ru.softdarom.qrcheck.events.service.TicketService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Mobile Events", description = "Контроллер взаимодействия с events для frontend-to-backend")
@RestController(value = "mobileEventController")
@RequestMapping("/mobile/events")
public class EventController {

    private final EventService eventService;
    private final TicketService ticketService;

    @Autowired
    EventController(EventService eventService, TicketService ticketService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
    }

    @ApiGetAllEvents
    @PageableAsQueryParam
    @GetMapping("/")
    public ResponseEntity<Page<EventResponse>> getAll(@RequestHeader(value = "X-Application-Version") String version,
                                                      @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(eventService.getAll(pageable));
    }

    @ApiGetEvent
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> get(@RequestHeader(value = "X-Application-Version") String version,
                                             @PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(eventService.getById(eventId));
    }

    //ToDo https://softdarom.myjetbrains.com/youtrack/issue/QRC-57
    @ApiPreSaveEvent
    @PostMapping(value = "/")
    public ResponseEntity<EventResponse> preSaveEvent(@RequestHeader(value = "X-Application-Version") String version) {
        return ResponseEntity.ok(eventService.preSave());
    }

    //ToDo https://softdarom.myjetbrains.com/youtrack/issue/QRC-57
    @ApiSaveEvent
    @PutMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<EventResponse> saveEvent(@RequestHeader(value = "X-Application-Version") String version,
                                                   @RequestBody @Valid EventRequest request) {
        return ResponseEntity.ok(eventService.endSave(request));
    }

    @ApiSaveEventImages
    @PostMapping(value = "/images/{eventId}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponse> saveEventImages(@RequestHeader(value = "X-Application-Version") String version,
                                                         @PathVariable("eventId") Long eventId,
                                                         @RequestParam("images") Collection<MultipartFile> images) {
        return ResponseEntity.ok(eventService.saveImages(eventId, images, ImageType.PHOTOGRAPHY));
    }

    @ApiSaveEventCover
    @PostMapping(value = "/images/cover/{eventId}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponse> saveEventCover(@RequestHeader(value = "X-Application-Version") String version,
                                                        @PathVariable("eventId") Long eventId,
                                                        @RequestParam("cover") MultipartFile cover) {
        return ResponseEntity.ok(eventService.saveImages(eventId, Set.of(cover), ImageType.COVER));
    }

    @ApiGetAllEventTypes
    @GetMapping("/types")
    public ResponseEntity<Set<EventType>> getAllTypes(@RequestHeader(value = "X-Application-Version") String version) {
        return ResponseEntity.ok(EnumSet.allOf(EventType.class));
    }

    @ApiGetAvailableTickets
    @GetMapping("/{eventId}/availableTickets")
    public ResponseEntity<Set<TicketDto>> getAvailableTickets(@RequestHeader(value = "X-Application-Version") String version,
                                                              @PathVariable @Min(0) Long eventId) {
        return ResponseEntity.ok(ticketService.getAvailableTickets(eventId));
    }
}