package ru.softdarom.qrcheck.events;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import ru.softdarom.qrcheck.events.config.property.LocaleProperties;
import ru.softdarom.qrcheck.events.config.property.LogbookProperties;
import ru.softdarom.qrcheck.events.config.property.OpenApiProperties;
import ru.softdarom.qrcheck.events.config.property.TaxProperties;

@Generated
@SpringBootApplication
@EnableWebSecurity
@EnableFeignClients
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableCaching
@EnableConfigurationProperties(
        {
                LogbookProperties.class,
                OpenApiProperties.class,
                TaxProperties.class,
                LocaleProperties.class
        }
)
public class EventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }

}
