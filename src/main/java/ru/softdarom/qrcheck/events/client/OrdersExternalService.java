package ru.softdarom.qrcheck.events.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.softdarom.qrcheck.events.config.FeignConfig;

@FeignClient(name = "orders", url = "${outbound.feign.orders.host}", configuration = FeignConfig.class)
public interface OrdersExternalService {

    @GetMapping("/internal/orders/exist/{eventId}")
    Boolean doesAnyOrderExist(
            @RequestHeader("X-ApiKey-Authorization") String apiKey,
            @PathVariable("eventId") Long eventId
    );
}