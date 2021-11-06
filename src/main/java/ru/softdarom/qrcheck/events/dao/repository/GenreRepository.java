package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.softdarom.qrcheck.events.dao.entity.GenreEntity;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
}