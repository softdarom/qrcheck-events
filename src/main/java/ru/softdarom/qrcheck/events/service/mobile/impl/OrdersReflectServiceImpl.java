package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.client.OrdersExternalService;
import ru.softdarom.qrcheck.events.service.mobile.OrdersReflectService;
import ru.softdarom.security.oauth2.config.property.ApiKeyProperties;

@Service
@Slf4j(topic = "SERVICE")
public class OrdersReflectServiceImpl implements OrdersReflectService {

    private final ApiKeyProperties properties;
    private final OrdersExternalService eventAccessService;

    @Autowired
    OrdersReflectServiceImpl(ApiKeyProperties properties,
                             OrdersExternalService eventAccessService) {
        this.properties = properties;
        this.eventAccessService = eventAccessService;
    }

    @Override
    public Boolean doesAnyOrderExist(Long eventId) {
        return eventAccessService.doesAnyOrderExist(properties.getToken().getOutgoing(), eventId).getBody();
    }

}
