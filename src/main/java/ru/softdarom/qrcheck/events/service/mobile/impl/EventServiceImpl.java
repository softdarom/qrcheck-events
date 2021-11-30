package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.mapper.impl.EventRequestMapper;
import ru.softdarom.qrcheck.events.mapper.impl.EventResponseMapper;
import ru.softdarom.qrcheck.events.model.base.ImageType;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerEventDto;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;
import ru.softdarom.qrcheck.events.service.mobile.EventImageService;
import ru.softdarom.qrcheck.events.service.mobile.EventService;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "SERVICE")
public class EventServiceImpl implements EventService {

    private final EventAccessService eventAccessService;
    private final EventRequestMapper eventRequestMapper;
    private final EventResponseMapper eventResponseMapper;
    private final EventImageService eventImageService;

    @Autowired
    EventServiceImpl(EventAccessService eventAccessService,
                     EventRequestMapper eventRequestMapper,
                     EventResponseMapper eventResponseMapper,
                     EventImageService eventImageService) {
        this.eventAccessService = eventAccessService;
        this.eventRequestMapper = eventRequestMapper;
        this.eventResponseMapper = eventResponseMapper;
        this.eventImageService = eventImageService;
    }

    @Override
    public EventResponse preSave() {
        var savedEvent = eventAccessService.save(new InnerEventDto());
        LOGGER.info("Pre-saving a new event for user: {}", savedEvent.getExternalUserId());
        LOGGER.info("The new event was saved, id: {}", savedEvent.getId());
        return new EventResponse(savedEvent.getId());
    }

    @Override
    public EventResponse endSave(EventRequest request) {
        Assert.notNull(request, "The 'request' must not be null!");
        Assert.isTrue(eventAccessService.exist(request.getId()), "A event must be created earlier!");
        LOGGER.info("Full information will be saved for an event {}", request.getId());
        var innerDto = eventRequestMapper.convertToDestination(request);
        var savedEvent = eventAccessService.save(innerDto);
        return eventResponseMapper.convertToDestination(savedEvent);
    }

    @Override
    public EventResponse getById(Long id) {
        Assert.notNull(id, "The 'id' must not be null!");
        LOGGER.info("Getting an event by id: {}", id);
        return eventResponseMapper.convertToDestination(eventAccessService.findById(id));
    }

    @Override
    public Page<EventResponse> getAll(Pageable pageable) {
        var authentication = getAuthentication();
        var externalUserId = (Long) authentication.getPrincipal();
        var authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        LOGGER.info("Getting all events for a user (id: {}) has roles: {}", externalUserId, authentication.getAuthorities());
        return getAllByRole(pageable, externalUserId, authorities);
    }

    private Page<EventResponse> getAllByRole(Pageable pageable, Long externalUserId, Set<String> authorities) {
        var commonUser = authorities.contains("ROLE_USER") || authorities.contains("ROLE_CHECKMAN");
        var promoter = authorities.contains("ROLE_PROMOTER");
        if (commonUser) {
            LOGGER.info("A client is not promoter: all events will be unloaded");
            return eventAccessService.findAllActual(pageable).map(eventResponseMapper::convertToDestination);
        } else if (promoter) {
            LOGGER.info("It is promoter. Events will be unloaded which were created the user");
            return eventAccessService.findAllByExternalUserId(externalUserId, pageable).map(eventResponseMapper::convertToDestination);
        } else {
            throw new UnsupportedOperationException("Unknown roles: " + authorities);
        }
    }

    @Override
    public EventResponse saveImages(Long eventId, Collection<MultipartFile> images, ImageType imageType) {
        Assert.notNull(eventId, "The 'eventId' must not be null!");
        Assert.notNull(images, "The 'images' must not be null!");
        Assert.notNull(imageType, "The 'imageType' must not be null!");
        LOGGER.info("Saving an image has a type: {}", imageType);
        return eventImageService.save(eventId, images, imageType);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}