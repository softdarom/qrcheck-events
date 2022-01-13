package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.softdarom.qrcheck.events.builder.ImageBuilder;
import ru.softdarom.qrcheck.events.client.ContentHandlerExternalService;
import ru.softdarom.qrcheck.events.dao.access.ImageAccessService;
import ru.softdarom.qrcheck.events.model.base.ImageType;
import ru.softdarom.qrcheck.events.model.dto.FileDto;
import ru.softdarom.qrcheck.events.model.dto.ImageDto;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalImageDto;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;
import ru.softdarom.qrcheck.events.model.dto.response.FileResponse;
import ru.softdarom.qrcheck.events.service.mobile.EventImageService;
import ru.softdarom.security.oauth2.config.property.ApiKeyProperties;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "SERVICE")
public class EventImageServiceImpl implements EventImageService {

    private static final String DEFAULT_VERSION = "v1.0.0";

    private final ApiKeyProperties apiKeyProperties;
    private final ContentHandlerExternalService contentHandlerExternalService;
    private final ImageAccessService imageAccessService;

    @Autowired
    EventImageServiceImpl(ApiKeyProperties apiKeyProperties,
                          ContentHandlerExternalService contentHandlerExternalService,
                          ImageAccessService imageAccessService) {
        this.apiKeyProperties = apiKeyProperties;
        this.contentHandlerExternalService = contentHandlerExternalService;
        this.imageAccessService = imageAccessService;
    }

    @Override
    public EventResponse save(Long eventId, Collection<MultipartFile> images, ImageType imageType) {
        var files = executeExternalSaver(images);
        var internalImages = files.stream()
                .map(it -> new InternalImageDto(it.getId(), imageType.isCover()))
                .collect(Collectors.toSet());
        var savedImages = new ImageBuilder(files, imageAccessService.save(eventId, internalImages)).build();
        return buildResponse(eventId, imageType, savedImages);
    }

    private EventResponse buildResponse(Long eventId, ImageType imageType, Collection<ImageDto> savedImages) {
        var response = new EventResponse(eventId);
        if (imageType.isCover()) {
            var cover = savedImages.stream().findAny().orElseThrow();
            response.setCover(cover);
        } else {
            response.setImages(savedImages);
        }
        return response;
    }

    private Set<FileDto> executeExternalSaver(Collection<MultipartFile> images) {
        LOGGER.info("Saving files in a storage via an external service");
        var response =
                contentHandlerExternalService.upload(
                        DEFAULT_VERSION,
                        apiKeyProperties.getToken().getOutgoing(),
                        images
                );
        var storedImages = Optional.ofNullable(response)
                .map(HttpEntity::getBody)
                .map(FileResponse::getImages)
                .orElse(Set.of());
        return Set.copyOf(storedImages);
    }
}