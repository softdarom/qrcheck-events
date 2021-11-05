package ru.softdarom.qrcheck.events.config.security;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import ru.softdarom.qrcheck.events.model.base.TokenValidType;
import ru.softdarom.qrcheck.events.model.dto.security.OAuth2TokenDto;
import ru.softdarom.qrcheck.events.service.AuthHandlerExternalService;

import java.util.Map;
import java.util.Optional;

@Slf4j(topic = "EVENTS-SECURITY")
public class RemoteOAuth2TokenService implements ResourceServerTokenServices {

    private static final AccessTokenConverter DEFAULT_ACCESS_TOKEN_CONVERTER = new DefaultAccessTokenConverter();

    private final String apiKey;
    private final AuthHandlerExternalService authHandlerExternalService;

    public RemoteOAuth2TokenService(String apiKey, AuthHandlerExternalService authHandlerExternalService) {
        this.apiKey = apiKey;
        this.authHandlerExternalService = authHandlerExternalService;
    }

    @Override
    @Cacheable(cacheNames = "loadAuthentication", cacheManager = "oAuth2CacheManager")
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        LOGGER.info("A user tries log in with an access token: '{}'", accessToken);
        var oAuthTokenInfo = verifyAccessToken(accessToken);
        if (!oAuthTokenInfo.getValid().isValid()) {
            LOGGER.warn("'{}' token is {}. Failure. Return.", accessToken, oAuthTokenInfo.getValid());
            return new FailureOAuthClientAuthentication();
        }
        var tokenInfo = DEFAULT_ACCESS_TOKEN_CONVERTER.extractAuthentication(createMapAuth(oAuthTokenInfo));
        var oAuth2 = new UsernamePasswordAuthenticationToken(oAuthTokenInfo.getUserId(), accessToken, tokenInfo.getAuthorities());
        var authentication = new OAuth2Authentication(tokenInfo.getOAuth2Request(), oAuth2);
        authentication.setAuthenticated(true);
        return authentication;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("The method is not supported!");
    }

    private Map<String, ?> createMapAuth(OAuth2TokenDto dto) {
        return Map.of(
                "client_id", String.valueOf(dto.getUserId()),
                "azp", dto.getAzp(),
                "aud", dto.getAud(),
                "sub", dto.getSub(),
                "authorities", dto.getScopes()
        );
    }

    private OAuth2TokenDto verifyAccessToken(String accessToken) {
        try {
            LOGGER.info("An access token will be verified via an external service.");
            return Optional.ofNullable(authHandlerExternalService.verify(apiKey, accessToken).getBody()).orElseThrow();
        } catch (FeignException e) {
            LOGGER.error("Feign client has returned an error! Return authorization error.", e);
            return new OAuth2TokenDto(TokenValidType.UNKNOWN);
        }
    }

    public static class FailureOAuthClientAuthentication extends OAuth2Authentication {

        public FailureOAuthClientAuthentication() {
            super(DEFAULT_ACCESS_TOKEN_CONVERTER.extractAuthentication(Map.of()).getOAuth2Request(), null);
        }

        @Override
        public boolean isAuthenticated() {
            return false;
        }

        @Override
        public Object getCredentials() {
            return "";
        }

        @Override
        public Object getPrincipal() {
            return "UNKNOWN";
        }

    }
}