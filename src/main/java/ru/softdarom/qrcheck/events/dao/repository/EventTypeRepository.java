package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.softdarom.qrcheck.events.dao.entity.EventTypeEntity;

public interface EventTypeRepository extends JpaRepository<EventTypeEntity, Long> {
}