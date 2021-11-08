package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.softdarom.qrcheck.events.dao.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Page<EventEntity> findAll(Pageable pageable);

    Page<EventEntity> findAllByExternalUserId(Long externalUserId, Pageable pageable);

}