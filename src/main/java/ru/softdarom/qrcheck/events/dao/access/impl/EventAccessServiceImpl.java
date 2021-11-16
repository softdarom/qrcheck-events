package ru.softdarom.qrcheck.events.dao.access.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.dao.repository.EventRepository;
import ru.softdarom.qrcheck.events.exception.NotFoundException;
import ru.softdarom.qrcheck.events.mapper.impl.InnerEventDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerEventDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Slf4j(topic = "ACCESS-SERVICE")
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
        LOGGER.debug("Сохранение события: {}", entity);
        var saved = eventRepository.save(entity);
        LOGGER.debug("Событие сохранено с id: {}", saved.getId());
        return eventMapper.convertToDestination(saved);
    }

    @Override
    @Transactional
    public boolean exist(Long id) {
        Assert.notNull(id, "The 'id' must not be null!");
        return eventRepository.existsById(id);
    }

    @Override
    @Transactional
    public InnerEventDto findById(Long id) {
        Assert.notNull(id, "The 'id' must not be null!");
        return eventRepository.findById(id)
                .map(eventMapper::convertToDestination)
                .orElseThrow(() -> new NotFoundException("The event not found by id " + id));
    }

    @Override
    @Transactional
    public Page<InnerEventDto> findAllActual(Pageable pageable) {
        return eventRepository.findAllByDraftIsFalseAndStartDateTimeAfter(LocalDateTime.now(), pageable).map(eventMapper::convertToDestination);
    }

    @Override
    @Transactional
    public Page<InnerEventDto> findAllByExternalUserId(Long externalUserId, Pageable pageable) {
        Assert.notNull(externalUserId, "The 'externalUserId' must not be null!");
        return eventRepository.findAllByExternalUserId(externalUserId, pageable).map(eventMapper::convertToDestination);
    }
}