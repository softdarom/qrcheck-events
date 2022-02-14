package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.mapper.impl.EventResponseMapper;
import ru.softdarom.qrcheck.events.mapper.impl.SavedEventRequestMapper;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;
import ru.softdarom.qrcheck.events.service.mobile.EventPresenterService;

@Service
@Slf4j(topic = "SERVICE")
public class EventPresenterServiceImpl implements EventPresenterService {

    private final SavedEventRequestMapper savedEventRequestMapper;
    private final EventResponseMapper eventResponseMapper;

    @Autowired
    EventPresenterServiceImpl(SavedEventRequestMapper savedEventRequestMapper, EventResponseMapper eventResponseMapper) {
        this.savedEventRequestMapper = savedEventRequestMapper;
        this.eventResponseMapper = eventResponseMapper;
    }

    @Override
    public EventResponse presentAsResponse(InternalEventDto dto) {
        return eventResponseMapper.convertToDestination(dto);
    }

    @Override
    public InternalEventDto presentAsInternalDto(EventRequest request) {
        return savedEventRequestMapper.convertToDestination(request);
    }
}
