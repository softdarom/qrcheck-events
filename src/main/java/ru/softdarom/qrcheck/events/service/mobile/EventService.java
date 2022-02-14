package ru.softdarom.qrcheck.events.service.mobile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.softdarom.qrcheck.events.model.base.ImageType;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;

import java.util.Collection;

public interface EventService {

    EventResponse preSave();

    EventResponse endSave(EventRequest request);

    EventResponse editEvent(EventRequest request);

    EventResponse getById(Long id);

    Page<EventResponse> getAll(Pageable pageable);

    EventResponse saveImages(Long eventId, Collection<MultipartFile> images, ImageType imageType);

    void deleteImages(Collection<Long> imageIds);
}