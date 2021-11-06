package ru.softdarom.qrcheck.events.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.softdarom.qrcheck.events.dao.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}