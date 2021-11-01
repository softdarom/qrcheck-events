package ru.softdarom.qrcheck.events.rest.controller.mobile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.softdarom.qrcheck.events.model.base.EventType;
import ru.softdarom.qrcheck.events.model.base.GenreType;
import ru.softdarom.qrcheck.events.model.base.TicketType;
import ru.softdarom.qrcheck.events.model.dto.*;
import ru.softdarom.qrcheck.events.model.dto.request.EventAddressRequest;
import ru.softdarom.qrcheck.events.model.dto.request.EventDescriptionRequest;
import ru.softdarom.qrcheck.events.model.dto.request.EventPeriodRequest;
import ru.softdarom.qrcheck.events.model.dto.request.EventTicketRequest;
import ru.softdarom.qrcheck.events.model.dto.response.BaseResponse;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static ru.softdarom.qrcheck.events.config.OpenApiConfig.BEARER_SECURITY_NAME;

@Tag(name = "Mobile Events", description = "Контроллер взаимодействия с events для frontend-to-backend")
@RestController(value = "mobileEventController")
@RequestMapping("/mobile/events")
public class EventController {

    @Operation(
            summary = "Получение всех событий",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME)
    )
    @ApiResponses(
            value = {
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
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Неавторизованный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @PageableAsQueryParam
    @GetMapping("/")
    public ResponseEntity<Page<EventResponse>> getAll(@RequestHeader(value = "X-Application-Version") String version,
                                                      @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(StubGenerator.generatePage(StubGenerator.generateFullEventResponse(), pageable));
    }

    @Operation(
            summary = "Получение события по id",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME)
    )
    @ApiResponses(
            value = {
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
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Отсутствует авторизация",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Неавторизованный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @PageableAsQueryParam
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> get(@RequestHeader(value = "X-Application-Version") String version,
                                             @PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(StubGenerator.generateFullEventResponse());
    }

    @Operation(
            summary = "Создание нового события",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME)
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новое событие создано",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Отсутствует авторизация",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Неавторизованный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @PostMapping("/")
    public ResponseEntity<EventResponse> saveEvent(@RequestHeader(value = "X-Application-Version") String version) {
        return ResponseEntity.ok(StubGenerator.generateIdEventResponse());
    }

    @Operation(
            summary = "Добавление адреса к ранее созданному событию",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME)
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Адрес добавлен",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Отсутствует авторизация",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Неавторизованный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @PostMapping("/address")
    public ResponseEntity<EventResponse> saveEventAddress(@RequestHeader(value = "X-Application-Version") String version,
                                                          @Valid @RequestBody EventAddressRequest request) {
        return ResponseEntity.ok(StubGenerator.generateAddressEventResponse());
    }

    @Operation(
            summary = "Добавление времени проведения к ранее созданному событию",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME)
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Адрес добавлен",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Отсутствует авторизация",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Неавторизованный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @PostMapping("/period")
    public ResponseEntity<EventResponse> saveEventPeriod(@RequestHeader(value = "X-Application-Version") String version,
                                                         @Valid @RequestBody EventPeriodRequest request) {
        return ResponseEntity.ok(StubGenerator.generatePeriodEventResponse());
    }

    @Operation(
            summary = "Добавление фотографий к ранее созданному событию",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME)
    )
    @ApiResponses(
            value = {
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
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Отсутствует авторизация",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Неавторизованный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @PostMapping(value = "/image", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponse> saveEventImage(@RequestHeader(value = "X-Application-Version") String version,
                                                        @RequestParam("images") Collection<MultipartFile> images) {
        return ResponseEntity.ok(StubGenerator.generateImagesEventResponse());
    }

    @Operation(
            summary = "Добавление обложки к ранее созданному событию",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME)
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обложка добавлена",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Отсутствует авторизация",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Неавторизованный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @PostMapping(value = "/cover", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponse> saveEventCover(@RequestHeader(value = "X-Application-Version") String version,
                                                        @RequestParam("cover") MultipartFile cover) {
        return ResponseEntity.ok(StubGenerator.generateCoverEventResponse());
    }

    @Operation(
            summary = "Добавление описания к ранее созданному событию",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME)
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Описание добавлено",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Отсутствует авторизация",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Неавторизованный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @PostMapping("/description")
    public ResponseEntity<EventResponse> saveEventDescription(@RequestHeader(value = "X-Application-Version") String version,
                                                              @Valid @RequestBody EventDescriptionRequest request) {
        return ResponseEntity.ok(StubGenerator.generateDescriptionEventResponse());
    }

    @Operation(
            summary = "Добавление билетов к ранее созданному событию",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME)
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Билеты добавлены",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Отсутствует авторизация",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Неавторизованный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @PostMapping("/tickets")
    public ResponseEntity<EventResponse> saveEventTicket(@RequestHeader(value = "X-Application-Version") String version,
                                                         @Valid @RequestBody EventTicketRequest request) {
        return ResponseEntity.ok(StubGenerator.generateTicketsEventResponse());
    }

    @Operation(
            summary = "Добавление дополнительных QR-кодов к ранее созданному событию",
            security = @SecurityRequirement(name = BEARER_SECURITY_NAME)
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "QR-коды добавлены",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Отсутствует авторизация",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Неавторизованный запрос",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @PostMapping("/option")
    public ResponseEntity<EventResponse> saveEventOption(@RequestHeader(value = "X-Application-Version") String version,
                                                         @Valid @RequestBody EventTicketRequest request) {
        return ResponseEntity.ok(StubGenerator.generateOptionsEventResponse());
    }

    @Operation(summary = "Получение всех типов событий")
    @ApiResponses(
            value = {
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
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @GetMapping("/type")
    public ResponseEntity<Collection<EventType>> getAllTypes(@RequestHeader(value = "X-Application-Version") String version) {
        return ResponseEntity.ok(EnumSet.allOf(EventType.class));
    }

    private static class StubGenerator {

        private static final long DEFAULT_PAGES_TOTAL = 1;
        private static final Long DEFAULT_ID = 1L;

        private static <T> Page<T> generatePage(T response, Pageable pageable) {
            return new PageImpl<>(List.of(response, response), pageable, DEFAULT_PAGES_TOTAL);
        }

        private static EventResponse generateFullEventResponse() {
            var response = new EventResponse();
            response.setEventId(DEFAULT_ID);
            response.setName("Имя события");
            response.setEvent(EventType.CONCERT);
            response.setAgeRestrictions("16+");
            response.setGenre(GenreType.ELECTRONICA);
            response.setActive(Boolean.TRUE);
            response.setDescription("Какое-то описание");
            response.setPeriod(generatePeriodDto());
            response.setAddress(generateAddressDto());
            response.setCover(generateImageDto());
            response.setImages(List.of(generateImageDto()));
            response.setTickets(List.of(generateTickerDto()));
            response.setOptions(List.of(generateOptionDto()));
            return response;
        }

        private static EventResponse generateIdEventResponse() {
            var response = new EventResponse();
            response.setEventId(DEFAULT_ID);
            return response;
        }

        private static EventResponse generateAddressEventResponse() {
            var response = new EventResponse();
            response.setEventId(DEFAULT_ID);
            response.setAddress(generateAddressDto());
            return response;
        }

        private static EventResponse generatePeriodEventResponse() {
            var response = new EventResponse();
            response.setEventId(DEFAULT_ID);
            response.setPeriod(generatePeriodDto());
            return response;
        }

        private static EventResponse generateImagesEventResponse() {
            var response = new EventResponse();
            response.setEventId(DEFAULT_ID);
            response.setImages(List.of(generateImageDto()));
            return response;
        }

        private static EventResponse generateCoverEventResponse() {
            var response = new EventResponse();
            response.setEventId(DEFAULT_ID);
            response.setCover(generateImageDto());
            return response;
        }

        private static EventResponse generateDescriptionEventResponse() {
            var response = new EventResponse();
            response.setEventId(DEFAULT_ID);
            response.setDescription("Какое-то описание");
            return response;
        }

        private static EventResponse generateTicketsEventResponse() {
            var response = new EventResponse();
            response.setEventId(DEFAULT_ID);
            response.setTickets(List.of(generateTickerDto()));
            return response;
        }

        private static EventResponse generateOptionsEventResponse() {
            var response = new EventResponse();
            response.setEventId(DEFAULT_ID);
            response.setOptions(List.of(generateOptionDto()));
            return response;
        }

        private static ImageDto generateImageDto() {
            var dto = new ImageDto();
            dto.setImageId(DEFAULT_ID);
            dto.setContent("https://www.golddisk.ru/goods_img/70/70501.jpg");
            dto.setFormat("JPG");
            return dto;
        }

        private static AddressDto generateAddressDto() {
            var dto = new AddressDto();
            dto.setAddressId(DEFAULT_ID);
            dto.setAddress("г. Москва, ул. Лубянка, 1");
            dto.setPlaceName("Имя места проведения события");
            return dto;
        }

        private static PeriodDto generatePeriodDto() {
            var dto = new PeriodDto();
            dto.setPeriodId(DEFAULT_ID);
            dto.setStartDate(LocalDate.of(2021, 12, 31));
            dto.setStartTime(LocalTime.of(23, 0));
            return dto;
        }

        private static TickerDto generateTickerDto() {
            var dto = new TickerDto();
            dto.setTicketId(DEFAULT_ID);
            dto.setPrice(1500.64);
            dto.setType(TicketType.PORTER);
            return dto;
        }

        private static OptionDto generateOptionDto() {
            var dto = new OptionDto();
            dto.setOptionId(DEFAULT_ID);
            dto.setName("Бар");
            dto.setCost(123.45);
            dto.setQuantity(100);
            return dto;
        }
    }
}