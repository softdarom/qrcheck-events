package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.client.CheckmanHandlerExternalService;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;
import ru.softdarom.qrcheck.events.model.base.RoleType;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.service.mobile.AbstractRoleService;
import ru.softdarom.security.oauth2.service.AuthExternalService;

import java.util.Set;

@Service
@Slf4j(topic = "SERVICE")
public class CheckmanServiceImpl extends AbstractRoleService {

    private final CheckmanHandlerExternalService checkmanHandler;
    private final AuthExternalService authExternalService;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    CheckmanServiceImpl(EventAccessService eventAccessService,
                        CheckmanHandlerExternalService checkmanHandler,
                        AuthExternalService authExternalService) {
        super(eventAccessService);
        this.checkmanHandler = checkmanHandler;
        this.authExternalService = authExternalService;
    }

    @Override
    public Page<InternalEventDto> getAll(Pageable pageable) {
        LOGGER.info("Client is a checkman: events will be unloaded which were created by own promoter");
        var promoterIds = extractPromoterIds();
        return eventAccessService.findAllByExternalUserIds(promoterIds, pageable);
    }

    private Set<Long> extractPromoterIds() {
        var response = checkmanHandler.getPromoterIds(authExternalService.getOutgoingToken(applicationName), getExternalUserId());
        return response.getBody();
    }

    @Override
    public RoleType getType() {
        return RoleType.CHECKMAN;
    }
}
