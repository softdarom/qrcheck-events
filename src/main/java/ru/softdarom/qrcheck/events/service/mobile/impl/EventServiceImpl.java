package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.mapper.impl.EventRequestMapper;
import ru.softdarom.qrcheck.events.mapper.impl.EventResponseMapper;
import ru.softdarom.qrcheck.events.model.base.ImageType;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;
import ru.softdarom.qrcheck.events.service.mobile.EventImageService;
import ru.softdarom.qrcheck.events.service.mobile.EventReflectorService;
import ru.softdarom.qrcheck.events.service.mobile.EventService;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j(topic = "SERVICE")
public class EventServiceImpl implements EventService {

    private final EventAccessService eventAccessService;
    private final EventRequestMapper eventRequestMapper;
    private final EventResponseMapper eventResponseMapper;
    private final EventImageService eventImageService;
    private final EventReflectorService eventReflectorService;

    @Autowired
    EventServiceImpl(EventAccessService eventAccessService,
                     EventRequestMapper eventRequestMapper,
                     EventResponseMapper eventResponseMapper,
                     EventImageService eventImageService,
                     EventReflectorService eventReflectorService) {
        this.eventAccessService = eventAccessService;
        this.eventRequestMapper = eventRequestMapper;
        this.eventResponseMapper = eventResponseMapper;
        this.eventImageService = eventImageService;
        this.eventReflectorService = eventReflectorService;
    }

    @Override
    public EventResponse preSave() {
        var savedEvent = eventAccessService.save(new InternalEventDto());
        LOGGER.info("Pre-saving a new event for user: {}", savedEvent.getExternalUserId());
        LOGGER.info("The new event was saved, id: {}", savedEvent.getId());
        return new EventResponse(savedEvent.getId());
    }

    @Override
    public EventResponse endSave(EventRequest request) {
        Assert.notNull(request, "The 'request' must not be null!");
        Assert.isTrue(eventAccessService.exist(request.getId()), "A event must be created earlier!");
        checkEditAccess(eventAccessService.findById(request.getId()).getExternalUserId());
        LOGGER.info("Full information will be saved for an event {}", request.getId());
        var internalDto = eventRequestMapper.convertToDestination(request);
        var savedEvent = eventAccessService.save(internalDto);
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
        Assert.notNull(pageable, "The 'pageable' must not ne null!");
        LOGGER.info("Getting all events");
        return eventReflectorService.getAllByRole(pageable).map(eventResponseMapper::convertToDestination);
    }

    @Override
    public EventResponse saveImages(Long eventId, Collection<MultipartFile> images, ImageType imageType) {
        Assert.notNull(eventId, "The 'eventId' must not be null!");
        Assert.notNull(images, "The 'images' must not be null!");
        Assert.notNull(imageType, "The 'imageType' must not be null!");
        Assert.isTrue(eventAccessService.exist(eventId), "A event must be created earlier!");
        checkEditAccess(eventAccessService.findById(eventId).getExternalUserId());
        LOGGER.info("Saving an image has a type: {}", imageType);
        return eventImageService.save(eventId, images, imageType);
    }

    @Override
    public void deleteImages(Collection<Long> imageIds) {
        Assert.notEmpty(imageIds, "The 'imageIds' must not be null or empty!");
        checkDeleteAccess(eventAccessService.findExternalUserIds(imageIds));
        LOGGER.info("Deleting images: {}", imageIds);
        eventImageService.removeAll(imageIds);
    }

    private void checkEditAccess(Long resourceCreatorId) {
        checkDeleteAccess(Set.of(resourceCreatorId));
    }

    private void checkDeleteAccess(Collection<Long> externalUserIds) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUserId = authentication.getPrincipal();
        LOGGER.info("Checking edit access a resource for an user: {}", currentUserId);
        var hasAccess = externalUserIds.stream().allMatch(it -> Objects.equals(it, currentUserId));
        if (hasAccess) {
            LOGGER.debug("Ids a promoter who created a resource and the current user are equal. Do nothing. Return.");
            return;
        }
        throw new AccessDeniedException("One or some resources were created another promoter!");
    }
}