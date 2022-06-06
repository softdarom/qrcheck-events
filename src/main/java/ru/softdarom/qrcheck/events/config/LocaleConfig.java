package ru.softdarom.qrcheck.events.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import ru.softdarom.qrcheck.events.config.property.LocaleProperties;

import java.util.ArrayList;

@Configuration
public class LocaleConfig {

    private final LocaleProperties properties;

    @Autowired
    LocaleConfig(LocaleProperties properties) {
        this.properties = properties;
    }

    @Bean
    LocaleResolver localeResolver() {
        var localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setSupportedLocales(new ArrayList<>(properties.getSupports()));
        localeResolver.setDefaultLocale(properties.getStandard());
        return localeResolver;
    }

    @Bean
    MessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(properties.getMessagePaths());
        messageSource.setDefaultEncoding(properties.getEncoding());
        return messageSource;
    }
}
