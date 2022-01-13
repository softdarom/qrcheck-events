package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.model.base.RoleType;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.service.mobile.AbstractRoleReflectService;

@Service
@Slf4j(topic = "SERVICE")
public class PromoterReflectServiceImpl extends AbstractRoleReflectService {

    @Autowired
    PromoterReflectServiceImpl(EventAccessService eventAccessService) {
        super(eventAccessService);
    }

    @Override
    public Page<InternalEventDto> getAll(Pageable pageable) {
        LOGGER.info("Client is a promoter: events will be unloaded which were created the user");
        return eventAccessService.findAllByExternalUserId(getExternalUserId(), pageable);
    }

    @Override
    public RoleType getType() {
        return RoleType.PROMOTER;
    }
}
