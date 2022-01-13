package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.softdarom.qrcheck.events.dao.entity.EventEntity;

import java.time.LocalDateTime;
import java.util.Set;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Page<EventEntity> findAllByDraftIsFalseAndStartDateTimeAfter(LocalDateTime now, Pageable pageable);

    Page<EventEntity> findAllByExternalUserId(Long externalUserId, Pageable pageable);

    Page<EventEntity> findAllByExternalUserIdIn(Set<Long> externalUserId, Pageable pageable);

}