package ru.softdarom.qrcheck.events.dao.access.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.dao.entity.EventEntity;
import ru.softdarom.qrcheck.events.dao.repository.EventRepository;
import ru.softdarom.qrcheck.events.exception.NotFoundException;
import ru.softdarom.qrcheck.events.mapper.impl.InternalEventDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "ACCESS-SERVICE")
public class EventAccessServiceImpl implements EventAccessService {

    private static final String NOT_NULL_ERROR_MESSAGE_PATTERN = "The '%s' must not be null!";

    private final EventRepository eventRepository;
    private final InternalEventDtoMapper eventMapper;

    @Autowired
    EventAccessServiceImpl(EventRepository eventRepository,
                           InternalEventDtoMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    @Transactional
    public InternalEventDto save(InternalEventDto dto) {
        var entity = eventMapper.convertToSource(dto);
        LOGGER.debug("A event will be saved: {}", entity);
        var saved = eventRepository.save(entity);
        LOGGER.debug("Event's id: {}", saved.getId());
        return eventMapper.convertToDestination(saved);
    }

    @Override
    @Transactional
    public boolean exist(Long id) {
        Assert.notNull(id, String.format(NOT_NULL_ERROR_MESSAGE_PATTERN, "id"));
        return eventRepository.existsById(id);
    }

    @Override
    @Transactional
    public boolean isDraft(Long id) {
        Assert.notNull(id, String.format(NOT_NULL_ERROR_MESSAGE_PATTERN, "id"));
        return eventRepository.findById(id)
                .map(EventEntity::getDraft)
                .orElseThrow(() -> new NotFoundException("The event not found by id " + id));
    }

    @Override
    @Transactional
    public InternalEventDto findById(Long id) {
        Assert.notNull(id, String.format(NOT_NULL_ERROR_MESSAGE_PATTERN, "id"));
        return eventRepository.findById(id)
                .map(eventMapper::convertToDestination)
                .orElseThrow(() -> new NotFoundException("The event not found by id " + id));
    }

    @Override
    @Transactional
    public Page<InternalEventDto> findAllActual(Pageable pageable) {
        return eventRepository.findAllByDraftIsFalseAndStartDateTimeAfter(LocalDateTime.now(), pageable).map(eventMapper::convertToDestination);
    }

    @Override
    @Transactional
    public Page<InternalEventDto> findAllByExternalUserId(Long externalUserId, Pageable pageable) {
        Assert.notNull(externalUserId, String.format(NOT_NULL_ERROR_MESSAGE_PATTERN, "externalUserId"));
        return eventRepository.findAllByExternalUserId(externalUserId, pageable).map(eventMapper::convertToDestination);
    }

    @Override
    @Transactional
    public Page<InternalEventDto> findAllByExternalUserIds(Collection<Long> externalUserIds, Pageable pageable) {
        Assert.notEmpty(externalUserIds, String.format(NOT_NULL_ERROR_MESSAGE_PATTERN, "externalUserIds"));
        return eventRepository.findAllByExternalUserIdIn(externalUserIds, pageable).map(eventMapper::convertToDestination);
    }

    @Override
    @Transactional
    public Set<InternalEventDto> findAllByIds(Collection<Long> eventsId) {
        Assert.notEmpty(eventsId, "The 'eventsId' must not be empty or null!");
        return eventRepository.findAllById(eventsId).stream()
                .map(eventMapper::convertToDestination)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Set<Long> findExternalUserIds(Collection<Long> imageIds) {
        Assert.notEmpty(imageIds, "The 'imageIds' must not be empty or null!");
        return eventRepository.findAllExternalUserIds(imageIds);
    }
}