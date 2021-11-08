package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.softdarom.qrcheck.events.dao.entity.GenreEntity;
import ru.softdarom.qrcheck.events.model.base.GenreType;

import java.util.Collection;
import java.util.Set;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    Set<GenreEntity> findAllByNameIn(Collection<GenreType> names);

}