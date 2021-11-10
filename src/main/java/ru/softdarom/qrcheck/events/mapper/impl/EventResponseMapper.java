package ru.softdarom.qrcheck.events.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.softdarom.qrcheck.events.ImageBuilder;
import ru.softdarom.qrcheck.events.config.property.ApiKeyProperties;
import ru.softdarom.qrcheck.events.mapper.AbstractDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerEventDto;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerImageDto;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;
import ru.softdarom.qrcheck.events.service.ContentHandlerExternalService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Component
public class EventResponseMapper extends AbstractDtoMapper<EventResponse, InnerEventDto> {

    private static final String DEFAULT_VERSION = "v1.0.0";

    private final ApiKeyProperties properties;
    private final ContentHandlerExternalService contentHandlerExternalService;

    protected EventResponseMapper(ModelMapper modelMapper,
                                  ApiKeyProperties properties,
                                  ContentHandlerExternalService contentHandlerExternalService) {
        super(modelMapper);
        this.properties = properties;
        this.contentHandlerExternalService = contentHandlerExternalService;
    }

    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(sourceClass, destinationClass);
        modelMapper
                .createTypeMap(destinationClass, sourceClass)
                .addMappings(mapping -> mapping.map(InnerEventDto::getType, EventResponse::setEvent))
                .setPostConverter(toSourceConverter(new SourceConverter()));
    }

    public class SourceConverter implements BiConsumer<InnerEventDto, EventResponse> {

        @Override
        public void accept(InnerEventDto destination, EventResponse source) {
            source.setActual(isActual(destination));
            setImages(source, destination.getImages());
        }

        private Boolean isActual(InnerEventDto destination) {
            return destination.getOverDate().isAfter(LocalDateTime.now()) && !destination.getDraft();
        }

        private void setImages(EventResponse source, Collection<InnerImageDto> images) {
            if (Objects.isNull(images) || images.isEmpty()) {
                return;
            }
            var externalImageIds = images.stream().map(InnerImageDto::getExternalImageId).collect(Collectors.toSet());
            var response =
                    contentHandlerExternalService.get(
                            DEFAULT_VERSION,
                            properties.getToken().getOutgoing(),
                            externalImageIds
                    ).getBody();
            var cover2Images = images.stream().collect(Collectors.partitioningBy(InnerImageDto::getCover));

            source.setCover(new ImageBuilder(response.getImages(), cover2Images.get(Boolean.TRUE)).build().stream().findAny().orElseThrow());
            source.setImages(new ImageBuilder(response.getImages(), cover2Images.get(Boolean.TRUE)).build());
        }
    }
}