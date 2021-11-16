package ru.softdarom.qrcheck.events.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String DISABLED_RESOURCE_ID = null;

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    ResourceServerConfig(@Qualifier("qrCheckAuthenticationProvider") AuthenticationProvider authenticationProvider,
                         @Qualifier("qrCheckAuthenticationEntryPoint") AuthenticationEntryPoint authenticationEntryPoint,
                         @Qualifier("qrCheckAccessDeniedHandler") AccessDeniedHandler accessDeniedHandler) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .csrf()
                    .disable()
                .anonymous()
                    .disable()
                .authorizeRequests(request -> request.anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(handlerConfigurer ->
                        handlerConfigurer
                            .authenticationEntryPoint(authenticationEntryPoint)
                            .accessDeniedHandler(accessDeniedHandler)
                )
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // @formatter:on
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId(DISABLED_RESOURCE_ID)
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

    }
}
