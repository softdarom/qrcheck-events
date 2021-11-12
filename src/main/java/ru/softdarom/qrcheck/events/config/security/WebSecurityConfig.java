package ru.softdarom.qrcheck.events.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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