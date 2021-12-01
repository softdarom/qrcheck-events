package ru.softdarom.qrcheck.events.config.swagger.classes;

import org.springframework.data.domain.PageImpl;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;

import java.util.List;

public final class PagedEventResponse extends PageImpl<EventResponse> {

    private PagedEventResponse() {
        super(List.of());
    }
}
