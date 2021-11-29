package ru.softdarom.qrcheck.events.service.external;

import ru.softdarom.qrcheck.events.model.dto.external.request.CheckEventRequest;
import ru.softdarom.qrcheck.events.model.dto.external.response.EventInfoResponse;

public interface BookService {

    EventInfoResponse getEventInfoAndBook(Long eventId, CheckEventRequest request);

    EventInfoResponse getEventInfoAndUnbooked(Long eventId, CheckEventRequest request);
}
