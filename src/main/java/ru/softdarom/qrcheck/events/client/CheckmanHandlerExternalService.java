package ru.softdarom.qrcheck.events.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.softdarom.qrcheck.events.config.FeignConfig;

import java.util.Set;
import java.util.UUID;

@FeignClient(name = "checkman-handler", url = "${outbound.feign.checkman-handler.host}", configuration = FeignConfig.class)
public interface CheckmanHandlerExternalService {

    @GetMapping("/internal/promoter/{checkmanId}")
    ResponseEntity<Set<Long>> getPromoterIds(
            @RequestHeader("X-ApiKey-Authorization") UUID apiKey,
            @PathVariable("checkmanId") Long checkmanId
    );

}
