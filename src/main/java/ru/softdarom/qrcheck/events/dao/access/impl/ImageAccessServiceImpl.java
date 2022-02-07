package ru.softdarom.qrcheck.events.dao.access.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.dao.access.ImageAccessService;
import ru.softdarom.qrcheck.events.dao.entity.ImageEntity;
import ru.softdarom.qrcheck.events.dao.repository.EventRepository;
import ru.softdarom.qrcheck.events.dao.repository.ImageRepository;
import ru.softdarom.qrcheck.events.exception.NotFoundException;
import ru.softdarom.qrcheck.events.mapper.impl.InternalImageDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalImageDto;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;

@Service
public class ImageAccessServiceImpl implements ImageAccessService {

    private final ImageRepository imageRepository;
    private final EventRepository eventRepository;
    private final InternalImageDtoMapper internalImageMapper;

    @Autowired
    ImageAccessServiceImpl(ImageRepository imageRepository,
                           EventRepository eventRepository,
                           InternalImageDtoMapper internalImageMapper) {
        this.imageRepository = imageRepository;
        this.eventRepository = eventRepository;
        this.internalImageMapper = internalImageMapper;
    }

    @Transactional
    @Override
    public Set<InternalImageDto> save(Long eventId, Collection<InternalImageDto> images) {
        Assert.notNull(eventId, "The 'eventId' must not be null!");
        Assert.notEmpty(images, "The 'images' must not be null or empty!");
        var event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Not found event by id: " + eventId));
        var imageEntities = internalImageMapper.convertToSources(images);
        for (ImageEntity entity : imageEntities) {
            entity.setEvent(event);
        }
        var savedImages = imageRepository.saveAll(imageEntities);
        return internalImageMapper.convertToDestinations(savedImages);
    }

    @Transactional
    @Override
    public void deleteAll(Collection<Long> imageIds) {
        Assert.notEmpty(imageIds, "The 'imageIds' must not be null or empty!");
        imageRepository.deleteAllByIdIn(imageIds);
    }
}