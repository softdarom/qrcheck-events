package ru.softdarom.qrcheck.events.rest.handler;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.softdarom.qrcheck.events.exception.InvalidBookOperation;
import ru.softdarom.qrcheck.events.exception.NotFoundException;
import ru.softdarom.qrcheck.events.model.dto.response.ErrorResponse;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j(topic = "EXCEPTION-HANDLER")
public class EventsExceptionHandler {

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

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorResponse> notFound(NotFoundException e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(InvalidBookOperation.class)
    ResponseEntity<ErrorResponse> notAcceptable(InvalidBookOperation e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(NOT_ACCEPTABLE).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> notValidRequest(MethodArgumentNotValidException e) {
        LOGGER.error(e.getMessage(), e);
        var result = e.getBindingResult();
        var errors = result.getAllErrors().stream()
                .map(it -> "Object '" + it.getObjectName() + "' has errors: " + it.getDefaultMessage())
                .collect(Collectors.joining("], [", "[", "]"));
        return ResponseEntity.badRequest().body(new ErrorResponse(errors));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> unknown(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
    }

}