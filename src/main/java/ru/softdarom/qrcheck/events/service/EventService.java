package ru.softdarom.qrcheck.events.service;

import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;

public interface EventService {

    EventResponse preSave();

    EventResponse endSave(EventRequest request);

}