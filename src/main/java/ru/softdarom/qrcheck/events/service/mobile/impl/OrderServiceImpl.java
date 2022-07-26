package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.client.OrdersExternalService;
import ru.softdarom.qrcheck.events.service.mobile.OrderService;
import ru.softdarom.security.oauth2.service.AuthExternalService;

@Service
@Slf4j(topic = "SERVICE")
public class OrderServiceImpl implements OrderService {

    private final OrdersExternalService eventAccessService;

    private final AuthExternalService authExternalService;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    OrderServiceImpl(OrdersExternalService eventAccessService, AuthExternalService authExternalService) {
        this.authExternalService = authExternalService;
        this.eventAccessService = eventAccessService;
    }

    @Override
    public Boolean doesAnyOrderExist(Long eventId) {
        return eventAccessService.doesAnyOrderExist(authExternalService.getOutgoingToken(applicationName), eventId);
    }

}
