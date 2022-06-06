package ru.softdarom.qrcheck.events.config.swagger.annotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.softdarom.qrcheck.events.model.dto.LocaleTypeDto;
import ru.softdarom.qrcheck.events.model.dto.response.ErrorResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;

@Target({METHOD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Получение всех типов событий",
        parameters = {
                @Parameter(
                        in = ParameterIn.HEADER,
                        name = "Accept-Language",
                        description = "Заголовок для установки языка локализации",
                        examples = {
                                @ExampleObject(name = "Russian", value = "ru", description = "Русский язык"),
                                @ExampleObject(name = "English", value = "en", description = "Английский язык")
                        },
                        schema = @Schema(implementation = String.class)
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "События получены",
                        content = {
                                @Content(
                                        mediaType = "application/json",
                                        array = @ArraySchema(
                                                schema = @Schema(implementation = LocaleTypeDto.class),
                                                uniqueItems = true
                                        )
                                )
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
public @interface ApiGetAllEventTypes {
}
