package ru.softdarom.qrcheck.events.service;

import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;

public interface EventService {

    EventResponse preSave(Long externalUserId);

}