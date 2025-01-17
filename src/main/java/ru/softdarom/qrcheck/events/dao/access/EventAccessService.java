package ru.softdarom.qrcheck.events.dao.access;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;

import java.util.Collection;
import java.util.Set;

public interface EventAccessService {

    InternalEventDto save(InternalEventDto dto);

    boolean exist(Long id);

    boolean isDraft(Long id);

    InternalEventDto findById(Long id);

    Page<InternalEventDto> findAllActual(Pageable pageable);

    Page<InternalEventDto> findAllByExternalUserId(Long externalUserId, Pageable pageable);

    Page<InternalEventDto> findAllByExternalUserIds(Collection<Long> externalUserIds, Pageable pageable);

    Set<InternalEventDto> findAllByIds(Collection<Long> eventsId);

    Set<Long> findExternalUserIds(Collection<Long> imageIds);

    Long getEventOwnerId(Long id);
}