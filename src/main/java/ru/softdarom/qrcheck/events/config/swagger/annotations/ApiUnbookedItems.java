package ru.softdarom.qrcheck.events.config.swagger.annotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ru.softdarom.qrcheck.events.model.dto.external.response.BookingInfoResponse;
import ru.softdarom.qrcheck.events.model.dto.response.ErrorResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static ru.softdarom.qrcheck.events.config.swagger.OpenApiConfig.API_KEY_SECURITY_NAME;

@Target({METHOD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Снятие бронирования с билетов и опций на событие",
        security = @SecurityRequirement(name = API_KEY_SECURITY_NAME),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Билеты и/или опции сняты с бронирования",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = BookingInfoResponse.class))
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
                        responseCode = "500", description = "Неизвестная ошибка",
                        content = {
                                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                        }
                )
        }
)
public @interface ApiUnbookedItems {
}
