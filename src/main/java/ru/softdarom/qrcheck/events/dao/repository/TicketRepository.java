package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ru.softdarom.qrcheck.events.dao.entity.TicketEntity;

import java.util.Set;

public interface TicketRepository extends CrudRepository<TicketEntity, Long> {

    Set<TicketEntity> findAllByEventIdAndEvent_Active(Long eventId, boolean eventActive);
}