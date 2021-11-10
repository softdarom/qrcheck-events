package ru.softdarom.qrcheck.events.dao.access.impl;

import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.ImageAccessService;
import ru.softdarom.qrcheck.events.dao.entity.ImageEntity;
import ru.softdarom.qrcheck.events.dao.repository.EventRepository;
import ru.softdarom.qrcheck.events.dao.repository.ImageRepository;
import ru.softdarom.qrcheck.events.exception.NotFoundException;
import ru.softdarom.qrcheck.events.mapper.impl.InnerImageDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerImageDto;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class ImageAccessServiceImpl implements ImageAccessService {

    private final ImageRepository imageRepository;
    private final EventRepository eventRepository;
    private final InnerImageDtoMapper innerImageMapper;

    @Autowired
    ImageAccessServiceImpl(ImageRepository imageRepository,
                           EventRepository eventRepository,
                           InnerImageDtoMapper innerImageMapper) {
        this.imageRepository = imageRepository;
        this.eventRepository = eventRepository;
        this.innerImageMapper = innerImageMapper;
    }

    @Transactional
    @Override
    public Collection<InnerImageDto> save(Long eventId, Collection<InnerImageDto> images) {
        Assert.notNull(eventId, "The 'eventId' must not be null!");
        Assert.notNull(images, "The 'eventId' must not be null!");
        var event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Not found event by id: " + eventId));
        var imageEntities = innerImageMapper.convertToSources(images);
        for (ImageEntity entity : imageEntities) {
            entity.setEvent(event);
        }
        var savedImages = imageRepository.saveAll(imageEntities);
        return innerImageMapper.convertToDestinations(savedImages);
    }
}