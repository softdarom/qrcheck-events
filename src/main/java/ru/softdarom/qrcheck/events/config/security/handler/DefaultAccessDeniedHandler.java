package ru.softdarom.qrcheck.events.config.security.handler;

import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Generated
public class DefaultAccessDeniedHandler extends DefaultExceptionHandler implements AccessDeniedHandler {

    public DefaultAccessDeniedHandler(ObjectMapper objectMapper, Tracer tracer) {
        super(objectMapper, tracer);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        doHandle(response, accessDeniedException);
    }
}