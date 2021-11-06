package ru.softdarom.qrcheck.events.dao.access;

import ru.softdarom.qrcheck.events.model.dto.inner.EventDto;

public interface EventAccessService {

    EventDto save(EventDto dto);

}