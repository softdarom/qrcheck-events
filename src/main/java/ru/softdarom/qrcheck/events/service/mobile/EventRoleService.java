package ru.softdarom.qrcheck.events.service.mobile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;

public interface EventRoleService {

    Page<EventResponse> getAllByRole(Pageable pageable);

}
