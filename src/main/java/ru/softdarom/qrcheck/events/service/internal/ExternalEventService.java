package ru.softdarom.qrcheck.events.service.internal;

import ru.softdarom.qrcheck.events.model.dto.external.response.EventInfoResponse;

import java.util.Collection;
import java.util.Set;

public interface ExternalEventService {

    Set<EventInfoResponse> getEventsInfo(Collection<Long> eventsId);

    EventInfoResponse getEventInfo(Long eventId);
}
