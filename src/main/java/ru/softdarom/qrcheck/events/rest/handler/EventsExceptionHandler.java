package ru.softdarom.qrcheck.events.rest.handler;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.softdarom.qrcheck.events.exception.NotFoundException;
import ru.softdarom.qrcheck.events.model.dto.response.ErrorResponse;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j(topic = "EXCEPTION-HANDLER")
public class EventsExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorResponse> notFound(NotFoundException e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({FeignException.Unauthorized.class})
    ResponseEntity<ErrorResponse> unauthorized(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
    }

    @ExceptionHandler({AccessDeniedException.class, FeignException.Forbidden.class})
    ResponseEntity<ErrorResponse> accessDenied(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(FORBIDDEN).body(new ErrorResponse("Access Denied"));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> unknown(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
    }

}