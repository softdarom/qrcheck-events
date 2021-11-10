package ru.softdarom.qrcheck.events.rest.controller.mobile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.softdarom.qrcheck.events.model.base.ImageType;
import ru.softdarom.qrcheck.events.model.base.EventType;
import ru.softdarom.qrcheck.events.model.base.GenreType;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;
import ru.softdarom.qrcheck.events.model.dto.response.ErrorResponse;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;
import ru.softdarom.qrcheck.events.service.EventService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static ru.softdarom.qrcheck.events.config.OpenApiConfig.BEARER_SECURITY_NAME;

@Tag(name = "Mobile Events", description = "Контроллер взаимодействия с events для frontend-to-backend")
@RestController(value = "mobileEventController")
@RequestMapping("/mobile/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(
            summary = "Получение всех событий",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "События получены",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
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
    @PageableAsQueryParam
    @GetMapping("/")
    public ResponseEntity<Page<EventResponse>> getAll(@RequestHeader(value = "X-Application-Version") String version,
                                                      @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(eventService.getAll(pageable));
    }

    @Operation(
            summary = "Получение события по id",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Событие получено",
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
                            responseCode = "404",
                            description = "Событие не найдено",
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
                            responseCode = "500", description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                            }
                    )
            }
    )
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> get(@RequestHeader(value = "X-Application-Version") String version,
                                             @PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(eventService.getById(eventId));
    }

    //ToDo https://softdarom.myjetbrains.com/youtrack/issue/QRC-57
    @Operation(
            summary = "Пре-создания нового события",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новое событие полностью создано",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    example = "{ \"eventId\": 0 }"
                                            )
                                    )
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
    @PostMapping(value = "/")
    public ResponseEntity<EventResponse> preSaveEvent(@RequestHeader(value = "X-Application-Version") String version) {
        return ResponseEntity.ok(eventService.preSave());
    }

    //ToDo https://softdarom.myjetbrains.com/youtrack/issue/QRC-57
    @Operation(
            summary = "Завершение создания нового события",
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
                            responseCode = "401",
                            description = "Отсутствует авторизация",
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
    @PutMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<EventResponse> saveEvent(@RequestHeader(value = "X-Application-Version") String version,
                                                   @RequestBody @Valid EventRequest request) {
        return ResponseEntity.ok(eventService.endSave(request));
    }

    @Operation(
            summary = "Добавление фотографий к событию",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фотографии добавлены",
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
                            description = "Событие не найдено",
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
    @PostMapping(value = "/images/{eventId}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponse> saveEventImages(@RequestHeader(value = "X-Application-Version") String version,
                                                         @PathVariable("eventId") Long eventId,
                                                         @RequestParam("images") Collection<MultipartFile> images) {
        return ResponseEntity.ok(eventService.saveImages(eventId, images, ImageType.PHOTOGRAPHY));
    }

    @Operation(
            summary = "Добавление обложки к событию",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обложка добавлены",
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
                            description = "Событие не найдено",
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
    @PostMapping(value = "/images/cover/{eventId}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponse> saveEventCover(@RequestHeader(value = "X-Application-Version") String version,
                                                        @PathVariable("eventId") Long eventId,
                                                        @RequestParam("cover") MultipartFile cover) {
        return ResponseEntity.ok(eventService.saveImages(eventId, Set.of(cover), ImageType.COVER));
    }

    @Operation(
            summary = "Получение всех типов событий",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "События получены",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = GenreType.class))
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
    public ResponseEntity<Collection<EventType>> getAllTypes(@RequestHeader(value = "X-Application-Version") String version) {
        return ResponseEntity.ok(EnumSet.allOf(EventType.class));
    }
}