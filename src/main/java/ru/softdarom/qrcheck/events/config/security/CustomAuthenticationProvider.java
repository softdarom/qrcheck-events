package ru.softdarom.qrcheck.events.config.security;

import lombok.Generated;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Generated
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authentication;
    }

    @Override
    public final boolean supports(Class<?> authentication) {
        return isPreAuthenticatedAuthenticationToken(authentication) || isFailureOAuthClientAuthentication(authentication);
    }

    private boolean isPreAuthenticatedAuthenticationToken(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private boolean isFailureOAuthClientAuthentication(Class<?> authentication) {
        return RemoteOAuth2TokenService.FailureOAuthClientAuthentication.class.isAssignableFrom(authentication);
    }
}