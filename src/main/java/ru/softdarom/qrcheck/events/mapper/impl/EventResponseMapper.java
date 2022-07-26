package ru.softdarom.qrcheck.events.mapper.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.softdarom.qrcheck.events.builder.ImageBuilder;
import ru.softdarom.qrcheck.events.client.ContentHandlerExternalService;
import ru.softdarom.qrcheck.events.mapper.AbstractDtoMapper;
import ru.softdarom.qrcheck.events.mapper.Checker;
import ru.softdarom.qrcheck.events.model.dto.PeriodDto;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalImageDto;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;
import ru.softdarom.qrcheck.events.model.dto.response.FileResponse;
import ru.softdarom.security.oauth2.service.AuthExternalService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Component
@Slf4j(topic = "MAPPER")
public class EventResponseMapper extends AbstractDtoMapper<InternalEventDto, EventResponse> implements Checker {

    private final ContentHandlerExternalService contentHandlerExternalService;
    private final AuthExternalService authExternalService;

    @Value("${spring.application.name}")
    private String applicationName;

    protected EventResponseMapper(ModelMapper modelMapper,
                                  ContentHandlerExternalService contentHandlerExternalService,
                                  AuthExternalService authExternalService) {
        super(modelMapper);
        this.authExternalService = authExternalService;
        this.contentHandlerExternalService = contentHandlerExternalService;
    }

    @Override
    protected void setupMapper() {
        modelMapper
                .createTypeMap(sourceClass, destinationClass)
                .setPostConverter(toDestinationConverter(new DestinationConverter()));
    }

    public class DestinationConverter implements BiConsumer<InternalEventDto, EventResponse> {

        @Override
        public void accept(InternalEventDto source, EventResponse destination) {
            whenNotNull(source.getOverDate(), it -> setActual(destination, source), () -> destination.setActual(false));
            whenNotNull(source.getStartDateTime(), it -> setPeriod(destination, it));
            whenNotNull(source.getImages(), it -> setImages(destination, it));
        }

        private void setActual(EventResponse destination, InternalEventDto source) {
            var actual = source.getOverDate().isAfter(LocalDateTime.now()) && !source.getDraft();
            destination.setActual(actual);
        }

        private void setPeriod(EventResponse destination, LocalDateTime startDateTime) {
            destination.setPeriod(new PeriodDto(startDateTime.toLocalDate(), startDateTime.toLocalTime()));
        }

        private void setImages(EventResponse destination, Collection<InternalImageDto> images) {
            if (Objects.isNull(images) || images.isEmpty()) {
                return;
            }
            var externalImageIds = images.stream().map(InternalImageDto::getExternalImageId).collect(Collectors.toSet());
            var response =
                    contentHandlerExternalService.get(
                            authExternalService.getOutgoingToken(applicationName),
                            externalImageIds
                    ).getBody();
            var cover2Images = images.stream().collect(Collectors.partitioningBy(InternalImageDto::getCover));

            setCover(destination, response, cover2Images);
            setImages(destination, response, cover2Images);
        }

        private void setCover(EventResponse destination, FileResponse response, Map<Boolean, List<InternalImageDto>> cover2Images) {
            if (Objects.isNull(response)) {
                LOGGER.info("A cover is not existed. Do nothing. Return.");
                return;
            }
            destination.setCover(
                    new ImageBuilder(
                            response.getImages(), cover2Images.getOrDefault(Boolean.TRUE, List.of())
                    ).build().stream().findAny().orElse(null)
            );
        }

        private void setImages(EventResponse destination, FileResponse response, Map<Boolean, List<InternalImageDto>> cover2Images) {
            if (Objects.isNull(response)) {
                LOGGER.info("Images are not existed. Do nothing. Return.");
                return;
            }
            destination.setImages(new ImageBuilder(response.getImages(), cover2Images.getOrDefault(Boolean.FALSE, List.of())).build());
        }
    }
}