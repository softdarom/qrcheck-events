package ru.softdarom.qrcheck.events.dao.access;

import ru.softdarom.qrcheck.events.model.dto.inner.InnerImageDto;

import java.util.Collection;

public interface ImageAccessService {

    Collection<InnerImageDto> save(Long eventId, Collection<InnerImageDto> images);

}