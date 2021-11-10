package ru.softdarom.qrcheck.events.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.ToString;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ru.softdarom.qrcheck.events.util.JsonHelper;

import javax.persistence.*;
import java.math.BigDecimal;

@Generated
@Data
@ToString(of = {"id", "name", "quantity", "availableQuantity", "cost", "price"})
@EqualsAndHashCode(of = {"id", "name", "quantity", "cost", "price"})
@Entity
@Table(name = "tickets")
@SQLDelete(sql = "UPDATE tickets SET active = false, updated = current_timestamp WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "active = true")
public class TicketEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticketSeqGenerator")
    @SequenceGenerator(name = "ticketSeqGenerator", sequenceName = "ticket_seq", allocationSize = 1)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @JsonIgnore
    @Column(name = "event_id", insertable = false, updatable = false)
    private Long eventId;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}