package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.model.base.RoleType;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.service.mobile.AbstractRoleService;

@Service
@Slf4j(topic = "SERVICE")
public class UserServiceImpl extends AbstractRoleService {

    @Autowired
    UserServiceImpl(EventAccessService eventAccessService) {
        super(eventAccessService);
    }

    @Override
    public Page<InternalEventDto> getAll(Pageable pageable) {
        LOGGER.info("Client is an user: all actual events will be showed");
        return eventAccessService.findAllActual(pageable);
    }

    @Override
    public RoleType getType() {
        return RoleType.USER;
    }
}
