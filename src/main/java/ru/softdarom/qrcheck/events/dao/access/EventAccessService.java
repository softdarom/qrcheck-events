package ru.softdarom.qrcheck.events.dao.access;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerEventDto;

public interface EventAccessService {

    InnerEventDto save(InnerEventDto dto);

    boolean exist(Long id);

    InnerEventDto findById(Long id);

    Page<InnerEventDto> findAllActual(Pageable pageable);

    Page<InnerEventDto> findAllByUserId(Long externalUserId, Pageable pageable);

}