package ru.softdarom.qrcheck.events.config.swagger.annotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ru.softdarom.qrcheck.events.model.dto.response.ErrorResponse;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static ru.softdarom.qrcheck.events.config.swagger.OpenApiConfig.BEARER_SECURITY_NAME;

@Target({METHOD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Редактирование события",
        security = @SecurityRequirement(name = BEARER_SECURITY_NAME),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Новое событие полностью создано",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
                        }
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Некорректный запрос",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                        }
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Отсутствует авторизация",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                        }
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Неавторизованный запрос",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                        }
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Существующие событие не найдено",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                        }
                ),
                @ApiResponse(
                        responseCode = "500", description = "Неизвестная ошибка",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                        }
                )
        }
)
public @interface ApiEditEvent {
}
