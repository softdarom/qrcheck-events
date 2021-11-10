package ru.softdarom.qrcheck.events.rest.controller.mobile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.softdarom.qrcheck.events.model.base.GenreType;
import ru.softdarom.qrcheck.events.model.dto.response.ErrorResponse;

import java.util.Collection;
import java.util.EnumSet;

@Tag(name = "Mobile Genres", description = "Контроллер взаимодействия с events для frontend-to-backend")
@RestController(value = "mobileGenreController")
@RequestMapping("/mobile/genres")
public class GenreController {

    @Operation(summary = "Получение всех жанров")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "События получены",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = GenreType.class))
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
    @GetMapping("/types")
    public ResponseEntity<Collection<GenreType>> getAll(@RequestHeader(value = "X-Application-Version") String version) {
        return ResponseEntity.ok(EnumSet.allOf(GenreType.class));
    }
}