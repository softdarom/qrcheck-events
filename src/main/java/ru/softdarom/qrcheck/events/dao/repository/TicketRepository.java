package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.softdarom.qrcheck.events.dao.entity.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
}