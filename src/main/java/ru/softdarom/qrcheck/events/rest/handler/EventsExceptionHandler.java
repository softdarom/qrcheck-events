package ru.softdarom.qrcheck.events.rest.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j(topic = "EVENTS-EXCEPTION-HANDLER")
public class EventsExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity<Void> unknown(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
    }

}