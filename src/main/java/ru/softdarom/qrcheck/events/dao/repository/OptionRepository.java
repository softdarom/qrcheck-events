package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.softdarom.qrcheck.events.dao.entity.OptionEntity;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {

    @Query(
            value = "select (case when count(o.id) = 0 then false else true end) " +
                    "from events.options o " +
                    "where o.event_id = :eventId",
            nativeQuery = true)
    boolean isEventHasOptions(@Param("eventId") Long eventId);
}