package ru.softdarom.qrcheck.events.service.mobile;

import org.springframework.web.multipart.MultipartFile;
import ru.softdarom.qrcheck.events.model.base.ImageType;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;

import java.util.Collection;

public interface EventImageService {

    EventResponse save(Long eventId, Collection<MultipartFile> images, ImageType imageType);

    void removeAll(Collection<Long> imageIds);
}