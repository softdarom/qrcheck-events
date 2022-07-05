package ru.softdarom.qrcheck.events.service.internal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.exception.NotFoundException;
import ru.softdarom.qrcheck.events.mapper.impl.ExternalEventResponseMapper;
import ru.softdarom.qrcheck.events.model.dto.external.response.EventInfoResponse;
import ru.softdarom.qrcheck.events.service.internal.ExternalEventService;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExternalEventServiceImpl implements ExternalEventService {

    private final EventAccessService eventAccessService;
    private final ExternalEventResponseMapper mapper;

    @Autowired
    ExternalEventServiceImpl(EventAccessService eventAccessService,
                                    ExternalEventResponseMapper mapper) {
        this.eventAccessService = eventAccessService;
        this.mapper = mapper;
    }

    @Override
    public Set<EventInfoResponse> getEventsInfo(Collection<Long> eventsId) {
        var events = eventAccessService.findAllByIds(eventsId).stream()
                .map(mapper::convertToDestination)
                .collect(Collectors.toSet());
        if (events.isEmpty()) {
            throw new NotFoundException("Events not found!");
        }
        return events;
    }

    @Override
    public EventInfoResponse getEventInfo(Long eventId) {
        return getEventsInfo(Set.of(eventId)).stream().findAny()
                .orElseThrow(() -> new NotFoundException("Not found event with id: " + eventId));
    }
}
