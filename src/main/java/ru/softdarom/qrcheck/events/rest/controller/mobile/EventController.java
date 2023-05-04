package ru.softdarom.qrcheck.events.rest.controller.mobile;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.softdarom.qrcheck.events.config.swagger.annotations.*;
import ru.softdarom.qrcheck.events.model.base.EventType;
import ru.softdarom.qrcheck.events.model.base.ImageType;
import ru.softdarom.qrcheck.events.model.dto.LocaleTypeDto;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;
import ru.softdarom.qrcheck.events.model.dto.response.TicketResponse;
import ru.softdarom.qrcheck.events.service.mobile.EventService;
import ru.softdarom.qrcheck.events.service.mobile.LocaleTypeService;
import ru.softdarom.qrcheck.events.service.mobile.TicketService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Mobile Events", description = "Контроллер взаимодействия с events")
@RestController(value = "mobileEventController")
@RequestMapping("/mobile/events")
public class EventController {

    private final EventService eventService;
    private final TicketService ticketService;

    private final LocaleTypeService<EventType> eventTypeService;

    @Autowired
    EventController(EventService eventService,
                    TicketService ticketService,
                    LocaleTypeService<EventType> eventTypeService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.eventTypeService = eventTypeService;
    }

    @ApiGetAllEvents
    @PageableAsQueryParam
    @GetMapping
    public Page<EventResponse> getAll(@RequestHeader(value = "X-Application-Version") String version,
                                      @ParameterObject Pageable pageable) {
        return eventService.getAll(pageable);
    }

    @ApiGetEvent
    @GetMapping("/{eventId}")
    public EventResponse get(@RequestHeader(value = "X-Application-Version") String version,
                             @PathVariable("eventId") Long eventId) {
        return eventService.getById(eventId);
    }

    @PreAuthorize("hasRole(T(ru.softdarom.qrcheck.events.model.base.RoleType.Ability).PROMOTER)")
    @ApiPreSaveEvent
    @PostMapping
    public EventResponse preSaveEvent(@RequestHeader(value = "X-Application-Version") String version) {
        return eventService.preSave();
    }

    @PreAuthorize("hasRole(T(ru.softdarom.qrcheck.events.model.base.RoleType.Ability).PROMOTER)")
    @ApiSaveEvent
    @PutMapping
    public EventResponse saveEvent(@RequestHeader(value = "X-Application-Version") String version,
                                   @RequestBody @Valid EventRequest request) {
        return eventService.endSave(request);
    }

    @PreAuthorize("hasRole(T(ru.softdarom.qrcheck.events.model.base.RoleType.Ability).PROMOTER)")
    @ApiEditEvent
    @PatchMapping
    public EventResponse editEvent(@RequestHeader(value = "X-Application-Version") String version,
                                   @RequestBody @Valid EventRequest request) {
        return eventService.editEvent(request);
    }

    @PreAuthorize("hasRole(T(ru.softdarom.qrcheck.events.model.base.RoleType.Ability).PROMOTER)")
    @ApiSaveEventImages
    @PostMapping(value = "/images/{eventId}", consumes = MULTIPART_FORM_DATA_VALUE)
    public EventResponse saveEventImages(@RequestHeader(value = "X-Application-Version") String version,
                                         @PathVariable("eventId") Long eventId,
                                         @RequestParam("images") Collection<MultipartFile> images) {
        return eventService.saveImages(eventId, images, ImageType.PHOTOGRAPHY);
    }

    @PreAuthorize("hasRole(T(ru.softdarom.qrcheck.events.model.base.RoleType.Ability).PROMOTER)")
    @ApiSaveEventCover
    @PostMapping(value = "/images/cover/{eventId}", consumes = MULTIPART_FORM_DATA_VALUE)
    public EventResponse saveEventCover(@RequestHeader(value = "X-Application-Version") String version,
                                        @PathVariable("eventId") Long eventId,
                                        @RequestParam("cover") MultipartFile cover) {
        return eventService.saveImages(eventId, Set.of(cover), ImageType.COVER);
    }

    @PreAuthorize("hasRole(T(ru.softdarom.qrcheck.events.model.base.RoleType.Ability).PROMOTER)")
    @ApiDeteleEventImage
    @DeleteMapping(value = "/images/{imageIds}")
    public void deleteEventCover(@RequestHeader(value = "X-Application-Version") String version,
                                 @NotEmpty @PathVariable("imageIds") Collection<Long> imageIds) {
        eventService.deleteImages(imageIds);
    }

    @ApiGetAllEventTypes
    @GetMapping("/types")
    public Set<LocaleTypeDto> getAllTypes(@RequestHeader(value = "X-Application-Version") String version,
                                          Locale locale) {
        return eventTypeService.getLocaleTypes(locale);
    }

    @ApiGetAvailableTickets
    @GetMapping("/{eventId}/tickets/available")
    public TicketResponse getAvailableTickets(@RequestHeader(value = "X-Application-Version") String version,
                                              @PathVariable @Min(0) Long eventId) {
        return ticketService.getAvailableTickets(eventId);
    }
}