package ru.softdarom.qrcheck.events.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import ru.softdarom.qrcheck.events.config.property.ApiKeyProperties;
import ru.softdarom.qrcheck.events.service.AuthHandlerExternalService;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String DISABLED_RESOURCE_ID = null;

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final AuthenticationEntryPoint defaultAuthenticationEntryPoint;
    private final AccessDeniedHandler defaultAccessDeniedHandler;

    @Autowired
    ResourceServerConfig(CustomAuthenticationProvider customAuthenticationProvider,
                         AuthenticationEntryPoint defaultAuthenticationEntryPoint,
                         AccessDeniedHandler defaultAccessDeniedHandler) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.defaultAuthenticationEntryPoint = defaultAuthenticationEntryPoint;
        this.defaultAccessDeniedHandler = defaultAccessDeniedHandler;
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
                .authorizeRequests(request ->
                        request
                                .antMatchers("/inner/**").hasRole("API_KEY")
                                .antMatchers("/mobile/**").hasAnyRole("USER")
                )
                .authenticationProvider(customAuthenticationProvider)
                .exceptionHandling(handlerConfigurer ->
                        handlerConfigurer
                            .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                            .accessDeniedHandler(defaultAccessDeniedHandler)
                )
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // @formatter:on
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId(DISABLED_RESOURCE_ID)
                .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                .accessDeniedHandler(defaultAccessDeniedHandler);

    }

    @Bean
    ResourceServerTokenServices tokenServices(ApiKeyProperties properties, AuthHandlerExternalService authHandlerExternalService) {
        return new RemoteOAuth2TokenService(properties.getToken().getOutgoing(), authHandlerExternalService);
    }

}
