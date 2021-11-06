package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.softdarom.qrcheck.events.dao.entity.OptionEntity;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {
}