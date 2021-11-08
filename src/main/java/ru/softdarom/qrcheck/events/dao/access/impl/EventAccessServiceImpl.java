package ru.softdarom.qrcheck.events.dao.access.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.dao.repository.EventRepository;
import ru.softdarom.qrcheck.events.mapper.impl.InnerEventDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerEventDto;

import javax.transaction.Transactional;

@Service
@Slf4j(topic = "EVENTS-ACCESS-SERVICE")
public class EventAccessServiceImpl implements EventAccessService {

    private final EventRepository eventRepository;
    private final InnerEventDtoMapper eventMapper;

    @Autowired
    EventAccessServiceImpl(EventRepository eventRepository,
                           InnerEventDtoMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    @Transactional
    public InnerEventDto save(InnerEventDto dto) {
        var entity = eventMapper.convertToSource(dto);
        LOGGER.debug("A event will be saved: {}", entity);
        var saved = eventRepository.save(entity);
        LOGGER.debug("Event's id: {}", saved.getId());
        return eventMapper.convertToDestination(saved);
    }

    @Override
    @Transactional
    public boolean exist(Long id) {
        Assert.notNull(id, "The 'id' must not be null!");
        return eventRepository.existsById(id);
    }
}