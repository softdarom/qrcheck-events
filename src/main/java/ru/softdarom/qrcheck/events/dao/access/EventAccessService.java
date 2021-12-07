package ru.softdarom.qrcheck.events.dao.access;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerEventDto;

import java.util.Collection;
import java.util.Set;

public interface EventAccessService {

    InnerEventDto save(InnerEventDto dto);

    boolean exist(Long id);

    InnerEventDto findById(Long id);

    Page<InnerEventDto> findAllActual(Pageable pageable);

    Page<InnerEventDto> findAllByExternalUserId(Long externalUserId, Pageable pageable);

    Set<InnerEventDto> findAllByIds(Collection<Long> eventsId);
}