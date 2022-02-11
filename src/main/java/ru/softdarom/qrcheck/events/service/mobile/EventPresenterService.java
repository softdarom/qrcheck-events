package ru.softdarom.qrcheck.events.service.mobile;

import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;

public interface EventPresenterService {

    EventResponse presentAsResponse(InternalEventDto dto);

    InternalEventDto presentAsInternalDto(EventRequest request);

}
