package ru.softdarom.qrcheck.events.config.security;

import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import ru.softdarom.qrcheck.events.config.security.handler.DefaultAccessDeniedHandler;
import ru.softdarom.qrcheck.events.config.security.handler.DefaultAuthenticationEntryPoint;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    AuthenticationEntryPoint defaultAuthenticationEntryPoint(ObjectMapper objectMapper, Tracer tracer) {
        return new DefaultAuthenticationEntryPoint(objectMapper, tracer);
    }

    @Bean
    AccessDeniedHandler defaultAccessDeniedHandler(ObjectMapper objectMapper, Tracer tracer) {
        return new DefaultAccessDeniedHandler(objectMapper, tracer);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-api/**"
                )
                .antMatchers(
                        "/actuator/health/**",
                        "/actuator/prometheus/**"
                )
                .antMatchers(
                        "/mobile/events/types",
                        "/mobile/genres/types"
                );
    }
}