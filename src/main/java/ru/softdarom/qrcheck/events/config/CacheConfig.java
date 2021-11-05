package ru.softdarom.qrcheck.events.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.config.property.OAuth2CacheProperties;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    CacheManager oAuth2CacheManager(OAuth2CacheProperties properties) {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                Assert.notNull(name, "The 'name' must not be null!");
                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                .maximumSize(properties.getSize())
                                .expireAfterWrite(properties.getExpireAfterWriteInSec(), TimeUnit.SECONDS)
                                .build()
                                .asMap(),
                        false);
            }
        };
    }
}