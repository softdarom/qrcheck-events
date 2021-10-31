package ru.softdarom.qrcheck.events.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.softdarom.qrcheck.events.model.dto.security.OAuth2TokenDto;

@FeignClient(name = "auth-handler", url = "${outbound.feign.auth-handler.host}")
public interface AuthHandlerExternalService {

    @GetMapping("/tokens/verify")
    ResponseEntity<OAuth2TokenDto> verify(@RequestHeader("X-ApiKey-Authorization") String apiKey, @RequestParam String accessToken);

}