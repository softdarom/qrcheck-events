package ru.softdarom.qrcheck.events.dao.access;

import ru.softdarom.qrcheck.events.model.dto.inner.InnerEventDto;

public interface EventAccessService {

    InnerEventDto save(InnerEventDto dto);

    boolean exist(Long id);

}