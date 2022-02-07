package ru.softdarom.qrcheck.events.dao.access;

import ru.softdarom.qrcheck.events.model.dto.internal.InternalImageDto;

import java.util.Collection;
import java.util.Set;

public interface ImageAccessService {

    Set<InternalImageDto> save(Long eventId, Collection<InternalImageDto> images);

    void deleteAll(Collection<Long> imageIds);
}