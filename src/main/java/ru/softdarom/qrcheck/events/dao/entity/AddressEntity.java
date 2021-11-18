package ru.softdarom.qrcheck.events.dao.entity;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ru.softdarom.qrcheck.events.util.JsonHelper;

import javax.persistence.*;

@Generated
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "addresses")
@SQLDelete(sql = "UPDATE addresses SET active = false, updated = current_timestamp WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "active = true")
public class AddressEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressSeqGenerator")
    @SequenceGenerator(name = "addressSeqGenerator", sequenceName = "address_seq", allocationSize = 1)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}