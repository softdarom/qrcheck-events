package ru.softdarom.qrcheck.events.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@Valid
@Getter
@Setter
@ConfigurationProperties("spring.cache.oauth2")
public class OAuth2CacheProperties {

    @Size(max = 3600) // should not be more hour
    private Long expireAfterWriteInSec;

    @Size(max = 5000000) // one string is 9 byte, 5kk * 9 = 45kk, it is 45mb of the heap
    private Long size;
}