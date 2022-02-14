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
import ru.softdarom.qrcheck.events.model.base.ImageType;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;
import ru.softdarom.qrcheck.events.service.mobile.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j(topic = "SERVICE")
public class EventServiceImpl implements EventService {

    private final EventPresenterService eventPresenterService;
    private final EventAccessService eventAccessService;
    private final EventImageService eventImageService;
    private final EventRoleService eventRoleService;
    private final OrderService orderService;

    @Autowired
    EventServiceImpl(EventPresenterService eventPresenterService,
                     EventAccessService eventAccessService,
                     EventImageService eventImageService,
                     EventRoleService eventRoleService,
                     OrderService orderService) {
        this.eventPresenterService = eventPresenterService;
        this.eventAccessService = eventAccessService;
        this.eventImageService = eventImageService;
        this.eventRoleService = eventRoleService;
        this.orderService = orderService;
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
        checkAccessEndSave(request.getId());
        LOGGER.info("Full information will be saved for an event: {}", request.getId());
        return edit(request);
    }

    @Override
    public EventResponse editEvent(EventRequest request) {
        Assert.notNull(request, "The 'request' must not be null!");
        checkAccessEdit(request.getId());
        LOGGER.info("Existed event will be edited: {}", request.getId());
        return edit(request);
    }

    private EventResponse edit(EventRequest request) {
        LOGGER.debug("New data: {}", request);
        var internalDto = eventPresenterService.presentAsInternalDto(request);
        return eventPresenterService.presentAsResponse(eventAccessService.save(internalDto));
    }

    @Override
    public EventResponse getById(Long eventId) {
        Assert.notNull(eventId, "The 'id' must not be null!");
        LOGGER.info("Getting an event by id: {}", eventId);
        return eventPresenterService.presentAsResponse(eventAccessService.findById(eventId));
    }

    @Override
    public Page<EventResponse> getAll(Pageable pageable) {
        Assert.notNull(pageable, "The 'pageable' must not ne null!");
        LOGGER.info("Getting all events");
        return eventRoleService.getAllByRole(pageable);
    }

    @Override
    public EventResponse saveImages(Long eventId, Collection<MultipartFile> images, ImageType imageType) {
        Assert.notNull(eventId, "The 'eventId' must not be null!");
        Assert.notNull(images, "The 'images' must not be null!");
        Assert.notNull(imageType, "The 'imageType' must not be null!");
        Assert.isTrue(eventAccessService.exist(eventId), "An event must be created earlier!");
        checkAccess(Set.of(eventAccessService.findById(eventId).getExternalUserId()));
        LOGGER.info("Saving an image has a type: {}", imageType);
        return eventImageService.save(eventId, images, imageType);
    }

    @Override
    public void deleteImages(Collection<Long> imageIds) {
        Assert.notEmpty(imageIds, "The 'imageIds' must not be null or empty!");
        checkAccess(eventAccessService.findExternalUserIds(imageIds));
        LOGGER.info("Deleting images: {}", imageIds);
        eventImageService.removeAll(imageIds);
    }

    private void checkAccessEndSave(Long eventId) {
        var draft = eventAccessService.isDraft(eventId);
        if (!draft) {
            throw new AccessDeniedException("The event has already been created fully established and cannot be 'post save'!");
        }
        var savedEvent = eventAccessService.findById(eventId);
        checkAccess(Set.of(savedEvent.getExternalUserId()));
        LOGGER.debug("The event {} can be 'post save'.", eventId);
    }

    private void checkAccessEdit(Long eventId) {
        LOGGER.info("Checking edit access the event : {}", eventId);
        var savedEvent = eventAccessService.findById(eventId);
        var externalUserId = savedEvent.getExternalUserId();
        checkAccess(Set.of(externalUserId));
        if (Boolean.TRUE.equals(savedEvent.getDraft())) {
            throw new AccessDeniedException("The event has not been created yet fully established and cannot be edited!");
        } else if (LocalDateTime.now().isAfter(savedEvent.getOverDate())) {
            throw new AccessDeniedException("The event has already been started and cannot be edited!");
        } else if (Boolean.TRUE.equals(orderService.doesAnyOrderExist(eventId))) {
            throw new AccessDeniedException("The event for which at least one ticket has been sold cannot be changed!");
        }
        LOGGER.debug("The event {} can be edited.", eventId);
    }

    private void checkAccess(Collection<Long> externalUserIds) {
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