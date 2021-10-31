package ru.softdarom.qrcheck.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.softdarom.qrcheck.events.config.property.ApiKeyProperties;
import ru.softdarom.qrcheck.events.config.property.LogbookProperties;
import ru.softdarom.qrcheck.events.config.property.SwaggerProperties;

@SpringBootApplication
@EnableConfigurationProperties(
        {
                LogbookProperties.class,
                SwaggerProperties.class,
                ApiKeyProperties.class
        }
)
public class EventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }

}
