package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.softdarom.qrcheck.events.dao.entity.ImageEntity;

import java.util.Collection;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    void deleteAllByIdIn(Collection<Long> imageIds);
}