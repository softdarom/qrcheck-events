package ru.softdarom.qrcheck.events.dao.entity;

import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.base.EventType;
import ru.softdarom.qrcheck.events.util.JsonHelper;

import javax.persistence.*;

@Generated
@Data
@Entity
@Table(
        name = "event_types",
        indexes = {
                @Index(name = "event_types_name_uindex", columnList = "name", unique = true)
        }
)
public class EventTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventTypesSeqGenerator")
    @SequenceGenerator(name = "eventTypesSeqGenerator", sequenceName = "event_type_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, updatable = false, insertable = false)
    private EventType name;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}