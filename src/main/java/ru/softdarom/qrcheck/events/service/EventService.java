package ru.softdarom.qrcheck.events.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;

public interface EventService {

    EventResponse preSave();

    EventResponse endSave(EventRequest request);

    EventResponse getById(Long id);

    Page<EventResponse> getAll(Pageable pageable);
}