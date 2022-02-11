package ru.softdarom.qrcheck.events.service.mobile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.softdarom.qrcheck.events.model.base.RoleType;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;

public interface RoleService {

    Page<InternalEventDto> getAll(Pageable pageable);

    RoleType getType();

}
