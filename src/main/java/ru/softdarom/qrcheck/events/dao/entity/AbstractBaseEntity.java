package ru.softdarom.qrcheck.events.dao.entity;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import ru.softdarom.qrcheck.events.model.base.ActiveType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Generated
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractBaseEntity {

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    @Column(name = "active", nullable = false)
    private ActiveType active = ActiveType.ENABLED;

    @PrePersist
    private void onCreate() {
        var current = LocalDateTime.now();
        created = current;
        updated = current;
    }

    @PreUpdate
    private void onUpdate() {
        updated = LocalDateTime.now();
    }

    @PreRemove
    private void onDelete() {
        updated = LocalDateTime.now();
        active = ActiveType.DISABLED;
    }
}