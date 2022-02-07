package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.softdarom.qrcheck.events.dao.entity.EventEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Page<EventEntity> findAllByDraftIsFalseAndStartDateTimeAfter(LocalDateTime now, Pageable pageable);

    Page<EventEntity> findAllByExternalUserId(Long externalUserId, Pageable pageable);

    Page<EventEntity> findAllByExternalUserIdIn(Collection<Long> externalUserId, Pageable pageable);

    @Query(value = "select e.external_user_id from images i inner join events e on e.id = i.event_id where i.id in (:imageIds)", nativeQuery = true)
//    @Query(value = "select e.externalUserId from ImageEntity i inner join EventEntity e on e.id = i.event.id where i.id in (:imageIds)")
    Set<Long> findAllExternalUserIds(@Param("imageIds") Collection<Long> imageIds);

}