package ru.softdarom.qrcheck.events.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.softdarom.qrcheck.events.config.property.OpenApiProperties;

@Configuration
public class OpenApiConfig {

    public static final String BEARER_SECURITY_NAME = "bearer";

    private static final String BEARER_TOKEN_HEADER_NAME = "Authorization";
    private static final String BEARER_TOKEN_DESCRIPTION = "Аутентификация через oAuth 2.0";
    private static final String LICENCE = "Лицензия API";

    private final OpenApiProperties openApiProperties;

    @Autowired
    OpenApiConfig(OpenApiProperties openApiProperties) {
        this.openApiProperties = openApiProperties;
    }

    @Bean
    public OpenAPI customOpenAPI(Info info, Components components) {
        return new OpenAPI()
                .components(components)
                .info(info);
    }

    @Bean
    Components components() {
        return new Components()
                .addSecuritySchemes(BEARER_SECURITY_NAME,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme(BEARER_SECURITY_NAME)
                                .in(SecurityScheme.In.HEADER)
                                .name(BEARER_TOKEN_HEADER_NAME)
                                .description(BEARER_TOKEN_DESCRIPTION)
                );
    }

    @Bean
    Info info(License license, Contact contact) {
        return new Info()
                .title(openApiProperties.getTitle())
                .version(openApiProperties.getVersion())
                .description(openApiProperties.getDescription())
                .license(license)
                .contact(contact);
    }

    @Bean
    License license() {
        return new License()
                .name(LICENCE)
                .url(openApiProperties.getLicenceUrl());
    }

    @Bean
    Contact contact() {
        return new Contact()
                .name(openApiProperties.getOwnerName())
                .email(openApiProperties.getOwnerEmail())
                .url(openApiProperties.getOwnerUrl());
    }

}