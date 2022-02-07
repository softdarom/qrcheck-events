package ru.softdarom.qrcheck.events.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ru.softdarom.qrcheck.events.util.JsonHelper;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Generated
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(
        callSuper = false,
        of = {"id", "externalUserId", "description", "startDateTime", "totalAmount", "currentAmount", "overDate", "draft"}
)
@Entity
@Table(name = "events")
@SQLDelete(sql = "UPDATE events SET active = false, updated = current_timestamp WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "active = true")
public class EventEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventSeqGenerator")
    @SequenceGenerator(name = "eventSeqGenerator", sequenceName = "event_seq", allocationSize = 1)
    private Long id;

    @Column(name = "external_user_id", nullable = false)
    private Long externalUserId;

    @Column(name = "name")
    private String name;

    @Column(name = "age_restrictions")
    private String ageRestrictions;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "current_amount")
    private BigDecimal currentAmount;

    @Column(name = "over_date")
    private LocalDateTime overDate;

    @Column(name = "draft")
    private Boolean draft;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "events_genres",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "genre_id"})
    )
    @ToString.Exclude
    private Set<GenreEntity> genres = new HashSet<>();

    @JsonIgnore
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE
            },
            orphanRemoval = true
    )
    @JoinColumn(name = "address_id")
    @ToString.Exclude
    private AddressEntity address;

    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "event_type_id")
    @ToString.Exclude
    private EventTypeEntity type;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", orphanRemoval = true)
    @ToString.Exclude
    private Set<ImageEntity> images = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", orphanRemoval = true)
    @ToString.Exclude
    private Set<OptionEntity> options = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", orphanRemoval = true)
    @ToString.Exclude
    private Set<TicketEntity> tickets = new HashSet<>();

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }

    public void setImages(Collection<ImageEntity> images) {
        this.images.clear();
        if (Objects.nonNull(images)) {
            this.images.addAll(images);
            this.images.forEach(it -> it.setEvent(this));
        }
    }

    public void setOptions(Collection<OptionEntity> options) {
        this.options.clear();
        if (Objects.nonNull(options)) {
            this.options.addAll(options);
            this.options.forEach(it -> it.setEvent(this));
        }
    }

    public void setTickets(Collection<TicketEntity> tickets) {
        this.tickets.clear();
        if (Objects.nonNull(tickets)) {
            this.tickets.addAll(tickets);
            this.tickets.forEach(it -> it.setEvent(this));
        }
    }

    public void addImage(ImageEntity image) {
        if (Objects.nonNull(image)) {
            this.images.add(image);
            image.setEvent(this);
        }
    }

    public void addOption(OptionEntity option) {
        if (Objects.nonNull(option)) {
            this.options.add(option);
            option.setEvent(this);
        }
    }

    public void addTicket(TicketEntity ticket) {
        if (Objects.nonNull(ticket)) {
            this.tickets.add(ticket);
            ticket.setEvent(this);
        }
    }
}