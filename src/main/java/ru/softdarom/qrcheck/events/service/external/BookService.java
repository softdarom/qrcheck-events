package ru.softdarom.qrcheck.events.service.external;

import ru.softdarom.qrcheck.events.model.dto.external.request.CheckEventRequest;
import ru.softdarom.qrcheck.events.model.dto.external.response.BookingInfoResponse;

public interface BookService {

    BookingInfoResponse bookItems(Long eventId, CheckEventRequest request);

    BookingInfoResponse unbookedItems(Long eventId, CheckEventRequest request);
}
