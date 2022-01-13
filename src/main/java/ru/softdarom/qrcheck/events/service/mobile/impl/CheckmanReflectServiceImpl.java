package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.client.CheckmanHandlerExternalService;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.model.base.RoleType;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.service.mobile.AbstractRoleReflectService;
import ru.softdarom.security.oauth2.config.property.ApiKeyProperties;

import java.util.Set;

@Service
@Slf4j(topic = "SERVICE")
public class CheckmanReflectServiceImpl extends AbstractRoleReflectService {

    private final CheckmanHandlerExternalService checkmanHandler;
    private final ApiKeyProperties properties;

    @Autowired
    CheckmanReflectServiceImpl(EventAccessService eventAccessService,
                               CheckmanHandlerExternalService checkmanHandler,
                               ApiKeyProperties properties) {
        super(eventAccessService);
        this.checkmanHandler = checkmanHandler;
        this.properties = properties;
    }

    @Override
    public Page<InternalEventDto> getAll(Pageable pageable) {
        LOGGER.info("Client is a checkman: events will be unloaded which were created by own promoter");
        var promoterIds = extractPromoterIds();
        return eventAccessService.findAllByExternalUserIds(promoterIds, pageable);
    }

    private Set<Long> extractPromoterIds() {
        var response = checkmanHandler.getPromoterIds(properties.getToken().getOutgoing(), getExternalUserId());
        return response.getBody();
    }

    @Override
    public RoleType getType() {
        return RoleType.CHECKMAN;
    }
}
