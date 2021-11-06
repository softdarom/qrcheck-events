package ru.softdarom.qrcheck.events.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.model.dto.inner.EventDto;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;
import ru.softdarom.qrcheck.events.service.EventService;

@Service
@Slf4j(topic = "EVENTS-SERVICE")
public class EventServiceImpl implements EventService {

    private final EventAccessService eventAccessService;

    @Autowired
    EventServiceImpl(EventAccessService eventAccessService) {
        this.eventAccessService = eventAccessService;
    }

    @Override
    public EventResponse preSave(Long externalUserId) {
        LOGGER.info("Pre-saving a new event for user: {}", externalUserId);
        var newEvent = new EventDto();
        newEvent.setExternalUserId(externalUserId);
        var savedEvent = eventAccessService.save(newEvent);
        LOGGER.info("The new event was saved, id: {}", savedEvent.getId());
        return new EventResponse(savedEvent.getId());
    }
}