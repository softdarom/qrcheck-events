package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.softdarom.qrcheck.events.dao.entity.EventTypeEntity;
import ru.softdarom.qrcheck.events.model.base.EventType;

public interface EventTypeRepository extends JpaRepository<EventTypeEntity, Long> {

    EventTypeEntity findByName(EventType name);

}