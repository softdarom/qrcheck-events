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

@Generated
@Data
@ToString(of = {"id", "externalImageId", "cover"})
@EqualsAndHashCode(of = {"id", "externalImageId", "cover"})
@Entity
@Table(name = "images")
@SQLDelete(sql = "UPDATE images SET active = false, updated = current_timestamp WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "active = true")
public class ImageEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imageSeqGenerator")
    @SequenceGenerator(name = "imageSeqGenerator", sequenceName = "image_seq", allocationSize = 1)
    private Long id;

    @Column(name = "external_image_id")
    private Long externalImageId;

    @Column(name = "cover")
    private Boolean cover;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}