package ru.softdarom.qrcheck.events.dao.access;

import ru.softdarom.qrcheck.events.model.dto.inner.InnerImageDto;

import java.util.Collection;
import java.util.Set;

public interface ImageAccessService {

    Set<InnerImageDto> save(Long eventId, Collection<InnerImageDto> images);

}