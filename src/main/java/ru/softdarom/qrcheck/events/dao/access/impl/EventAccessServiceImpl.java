package ru.softdarom.qrcheck.events.dao.access.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.dao.repository.EventRepository;
import ru.softdarom.qrcheck.events.mapper.impl.EventDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.inner.EventDto;

import javax.transaction.Transactional;

@Service
@Slf4j(topic = "EVENTS-ACCESS-SERVICE")
public class EventAccessServiceImpl implements EventAccessService {

    private final EventRepository eventRepository;
    private final EventDtoMapper eventMapper;

    @Autowired
    EventAccessServiceImpl(EventRepository eventRepository,
                           EventDtoMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    @Transactional
    public EventDto save(EventDto dto) {
        LOGGER.debug("A event will be saved. Dto: {}", dto);
        var saved = eventRepository.save(eventMapper.convertToSource(dto));
        LOGGER.debug("Event's id: {}", saved.getId());
        return eventMapper.convertToDestination(saved);
    }
}