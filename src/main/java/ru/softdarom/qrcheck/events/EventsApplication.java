package ru.softdarom.qrcheck.events;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import ru.softdarom.qrcheck.events.config.property.ApiKeyProperties;
import ru.softdarom.qrcheck.events.config.property.LogbookProperties;
import ru.softdarom.qrcheck.events.config.property.OAuth2CacheProperties;
import ru.softdarom.qrcheck.events.config.property.SwaggerProperties;

@Generated
@SpringBootApplication
@EnableWebSecurity
@EnableFeignClients
@EnableResourceServer
@EnableCaching
@EnableConfigurationProperties(
        {
                LogbookProperties.class,
                SwaggerProperties.class,
                ApiKeyProperties.class,
                OAuth2CacheProperties.class
        }
)
public class EventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }

}
