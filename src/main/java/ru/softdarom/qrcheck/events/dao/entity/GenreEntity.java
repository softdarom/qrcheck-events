package ru.softdarom.qrcheck.events.dao.entity;

import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.model.base.GenreType;
import ru.softdarom.qrcheck.events.util.JsonHelper;

import javax.persistence.*;

@Generated
@Data
@Entity
@Table(
        name = "genres",
        indexes = {
                @Index(name = "genres_name_uindex", columnList = "name", unique = true)
        }
)
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genreSeqGenerator")
    @SequenceGenerator(name = "genreSeqGenerator", sequenceName = "genre_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, updatable = false, insertable = false)
    private GenreType name;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}